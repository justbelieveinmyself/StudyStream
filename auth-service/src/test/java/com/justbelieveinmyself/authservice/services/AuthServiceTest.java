package com.justbelieveinmyself.authservice.services;

import com.justbelieveinmyself.authservice.apiclients.CourseServiceClient;
import com.justbelieveinmyself.authservice.apiclients.MailServiceClient;
import com.justbelieveinmyself.authservice.apiclients.UserServiceClient;
import com.justbelieveinmyself.authservice.domains.dtos.LoginRequestDto;
import com.justbelieveinmyself.authservice.domains.dtos.RefreshResponseDto;
import com.justbelieveinmyself.authservice.domains.dtos.RegisterDto;
import com.justbelieveinmyself.authservice.domains.dtos.UserDto;
import com.justbelieveinmyself.authservice.domains.entities.User;
import com.justbelieveinmyself.authservice.repository.UserRepository;
import com.justbelieveinmyself.library.enums.Role;
import com.justbelieveinmyself.library.exception.ConflictException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @InjectMocks
    private AuthService authService;
    @Mock
    private EmailService emailService;
    @Mock
    private UserServiceClient userServiceClient;
    @Mock
    private MailServiceClient mailServiceClient;
    @Mock
    private CourseServiceClient courseServiceClient;
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private RefreshTokenService refreshTokenService;

    @Test
    @DisplayName("Registration with valid request")
    void register_shouldRegisterUser() {
        final Long id = 1L;
        final String username = "username";
        final String email = "my@email.com";
        final String password = "1234";
        final String firstName = "firstName";
        final String lastName = "lastName";
        final String phone = "+79718009324";


        RegisterDto registerDto = RegisterDto.builder()
                .username(username)
                .email(email)
                .password(password)
                .firstName(firstName)
                .lastName(lastName)
                .phone(phone).build();

        when(userRepository.existsByUsernameOrEmail(username, email))
                .thenReturn(false);

        when(userRepository.save(any()))
                .thenAnswer(i -> {
                    User savedUser = (User) i.getArguments()[0];
                    savedUser.setId(id);
                    return savedUser;
                });


        UserDto response = authService.register(registerDto);


        assertEquals(response.getRoles(), new HashSet<>(Arrays.asList(Role.STUDENT)));
        verify(userRepository, times(1)).existsByUsernameOrEmail(username, email);
        verify(passwordEncoder, times(1)).encode(password);
        verify(userRepository, times(1)).save(any(User.class));
        verify(userServiceClient, times(1)).createUser(any(UserDto.class));
        verify(courseServiceClient, times(1)).createUser(id);
        verify(mailServiceClient, times(1)).createUser(id, email, username);
        verify(emailService, times(1)).sendActivationCode(eq(username), eq(email), anyString());
        assertEquals(id, response.getId());
        assertEquals(username, response.getUsername());
        assertEquals(email, response.getEmail());
        assertEquals(firstName, response.getFirstName());
        assertEquals(lastName, response.getLastName());
        assertEquals(phone, response.getPhone());
    }

    @Test
    @DisplayName("Registration with invalid request must throw ConflictException")
    void register_shouldThrowConflictException() {
        when(userRepository.existsByUsernameOrEmail(anyString(), anyString())).thenReturn(true);

        Assertions.assertThrows(ConflictException.class,
                () -> authService.register(RegisterDto.builder().username(anyString()).email(anyString()).build()),
                "User with username or email already exists!");

        verify(userRepository, times(1)).existsByUsernameOrEmail(anyString(), anyString());
    }

    @Test
    @DisplayName("Successful login into")
    void login() {
        final String username = "username";
        final String password = "1234";
        final String refreshToken = "refreshToken";
        final Instant refreshTokenExpiration = Instant.now();
        final String accessToken = "accessToken";
        Instant accessTokenExpiration = Instant.now().plusMillis(2000);

        LoginRequestDto loginRequestDto = LoginRequestDto.builder()
                .username(username)
                .password(password).build();

        User user = User.builder().username(username).build();

        RefreshResponseDto refreshResponseDto = RefreshResponseDto.builder()
                .refreshToken(refreshToken)
                .refreshTokenExpiration(refreshTokenExpiration)
                .accessToken(accessToken)
                .accessTokenExpiration(accessTokenExpiration).build();

        when(userRepository.findByUsername(username)).thenReturn(Optional.ofNullable(user));
        when(refreshTokenService.createRefreshToken(user)).thenAnswer(i -> refreshResponseDto);

        RefreshResponseDto response = authService.login(loginRequestDto);

        verify(authenticationManager, times(1)).authenticate(new UsernamePasswordAuthenticationToken(username, password));
        verify(userRepository, times(1)).findByUsername(username);
        verify(refreshTokenService, times(1)).createRefreshToken(user);
        assertEquals(response, refreshResponseDto);
    }

}