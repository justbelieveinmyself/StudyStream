package com.justbelieveinmyself.authservice.services;

import com.justbelieveinmyself.authservice.domains.dtos.LoginRequestDto;
import com.justbelieveinmyself.authservice.domains.dtos.RefreshResponseDto;
import com.justbelieveinmyself.authservice.domains.dtos.RegisterDto;
import com.justbelieveinmyself.authservice.domains.dtos.UserDto;
import com.justbelieveinmyself.authservice.domains.entities.User;
import com.justbelieveinmyself.authservice.repository.UserRepository;
import com.justbelieveinmyself.library.enums.Role;
import com.justbelieveinmyself.library.exception.ConflictException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.Ref;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {
    @InjectMocks
    private AuthService authService;
    @Mock
    private EmailService emailService;
    @Mock
    private KafkaTemplate<String, UserDto> userDtoKafkaTemplate;
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
        final String username = "username";
        final String email = "my@email.com";
        final String password = "1234";
        RegisterDto registerDto = new RegisterDto();
        registerDto.setUsername(username);
        registerDto.setEmail(email);
        registerDto.setPassword(password);
        when(userRepository.existsByUsernameOrEmail(registerDto.getUsername(), registerDto.getEmail())).thenReturn(false);
        when(userRepository.save(any())).thenAnswer(i -> i.getArguments()[0]);

        UserDto response = authService.register(registerDto);

        assertEquals(response.getUsername(), username);
        assertEquals(response.getEmail(), email);
        assertEquals(response.getRoles(), new HashSet<>(Arrays.asList(Role.STUDENT)));
        verify(userRepository, times(1)).existsByUsernameOrEmail(username, email);
        verify(userDtoKafkaTemplate, times(1)).send(eq("user-registration-topic"), any(UserDto.class));
        verify(emailService, times(1)).sendActivationCode(eq(username), eq(email), anyString());
        verify(passwordEncoder, times(1)).encode(password);
    }

    @Test
    @DisplayName("Registration with invalid request must throw ConflictException")
    void register_shouldThrowConflictException() {
        RegisterDto registerDto = new RegisterDto();
        registerDto.setUsername("username");
        registerDto.setEmail("my@email.com");
        registerDto.setPassword("1234");
        when(userRepository.existsByUsernameOrEmail(registerDto.getUsername(), registerDto.getEmail())).thenReturn(true);

        Assertions.assertThrows(ConflictException.class,
                () -> authService.register(registerDto),
                "User with username or email already exists!");

        verify(userRepository, times(1)).existsByUsernameOrEmail(registerDto.getUsername(), registerDto.getEmail());
    }

    @Test
    @DisplayName("Login")
    void login() {
        final String username = "username";
        final String password = "1234";
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setUsername(username);
        loginRequestDto.setPassword(password);
        User user = new User();
        user.setUsername(username);
        when(userRepository.findByUsername(username)).thenReturn(Optional.ofNullable(user));

        RefreshResponseDto response = authService.login(loginRequestDto);

        verify(authenticationManager, times(1)).authenticate(new UsernamePasswordAuthenticationToken(username, password));
        verify(userRepository, times(1)).findByUsername(username);
        verify(refreshTokenService, times(1)).createRefreshToken(any(User.class));
    }

}