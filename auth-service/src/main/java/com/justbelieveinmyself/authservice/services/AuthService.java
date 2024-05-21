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
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;
    private final EmailService emailService;
    private final UserServiceClient userServiceClient;
    private final CourseServiceClient courseServiceClient;
    private final MailServiceClient mailServiceClient;

    @Transactional
    public UserDto register(RegisterDto registerDto) {
        if (userRepository.existsByUsernameOrEmail(registerDto.getUsername(), registerDto.getEmail())) {
            throw new ConflictException("User with username or email already exists!");
        }
        User user = createUserFromRegisterDto(registerDto);

        User savedUser = userRepository.save(user);

        UserDto userDto = new UserDto().fromEntity(savedUser);
        userDto.setFirstName(registerDto.getUsername());
        userDto.setLastName(registerDto.getLastName());
        userDto.setPhone(registerDto.getPhone());

        userServiceClient.createUser(userDto);
        courseServiceClient.createUser(userDto.getId());
        mailServiceClient.createUser(userDto.getId(), userDto.getEmail(), userDto.getUsername());

        emailService.sendActivationCode(userDto.getUsername(), userDto.getEmail(), savedUser.getActivationCode());

        return userDto;
    }

    public RefreshResponseDto login(LoginRequestDto loginRequestDto) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword()));
        User user = userRepository.findByUsername(loginRequestDto.getUsername()).get();
        RefreshResponseDto refreshResponseDto = refreshTokenService.createRefreshToken(user);
        return refreshResponseDto;
    }

    private User createUserFromRegisterDto(RegisterDto registerDto) {
        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setEmail(registerDto.getEmail());
        user.setRoles(Set.of(Role.STUDENT));
        user.setActivationCode(UUID.randomUUID().toString());
        return user;
    }

}
