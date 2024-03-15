package com.justbelieveinmyself.authservice.services;

import com.justbelieveinmyself.authservice.domains.dtos.*;
import com.justbelieveinmyself.authservice.domains.entities.User;
import com.justbelieveinmyself.authservice.exceptions.EmailVerificationException;
import com.justbelieveinmyself.authservice.exceptions.UnauthorizedException;
import com.justbelieveinmyself.authservice.repository.UserRepository;
import com.justbelieveinmyself.library.enums.Role;
import com.justbelieveinmyself.library.exception.UsernameOrEmailAlreadyExistsException;
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
    private final KafkaTemplate<String, UserDto> userDtoKafkaTemplate;
    private final KafkaTemplate<String, EmailVerificationDto> stringKafkaTemplate;

    @Transactional
    public UserDto register(RegisterDto registerDto) {
        if (userRepository.existsByUsernameOrEmail(registerDto.getUsername(), registerDto.getEmail())) {
            throw new UsernameOrEmailAlreadyExistsException("User with username or email already exists!");
        }
        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setEmail(registerDto.getEmail());
        user.setRoles(Set.of(Role.STUDENT));
        String activationCode = UUID.randomUUID().toString();
        user.setActivationCode(activationCode);

        User savedUser = userRepository.save(user);

        UserDto userDto = new UserDto().fromEntity(savedUser);
        userDto.setFirstName(registerDto.getUsername());
        userDto.setLastName(registerDto.getLastName());
        userDto.setPhone(registerDto.getPhone());
        userDtoKafkaTemplate.send("user-registration-topic", userDto);

        EmailVerificationDto emailVerificationDto = new EmailVerificationDto(userDto.getUsername(), userDto.getEmail(), activationCode);
        stringKafkaTemplate.send("user-email-verify-topic", emailVerificationDto);

        return userDto;
    }

    public RefreshResponseDto login(LoginRequestDto loginRequestDto) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword()));
        User user = userRepository.findByUsername(loginRequestDto.getUsername()).get();
        RefreshResponseDto refreshResponseDto = refreshTokenService.createRefreshToken(user);
        return refreshResponseDto;
    }

    public RefreshResponseDto refreshToken(RefreshRequestDto refreshRequestDto) {
        return refreshTokenService.refreshToken(refreshRequestDto);
    }

    @Transactional
    public void updateEmail(User authedUser, UpdateEmailDto requestDto) {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new UsernameOrEmailAlreadyExistsException("User with email already exists!");
        }
        authedUser.setEmail(requestDto.getEmail());
        userRepository.save(authedUser);
        // TODO: send this change to user-email topic and invoke email service to send verification
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UnauthorizedException("Bearer token not found!"));
    }

    public void verifyEmail(String activationCode) {
        User user = userRepository.findByActivationCode(activationCode).orElseThrow(() -> new EmailVerificationException("Activation code already used!"));
        user.setActivationCode(null);
        userRepository.save(user);
    }
}
