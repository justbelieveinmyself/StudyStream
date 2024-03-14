package com.justbelieveinmyself.authservice.services;

import com.justbelieveinmyself.authservice.domains.dtos.*;
import com.justbelieveinmyself.authservice.domains.entities.User;
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

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;
    private final KafkaTemplate<String, UserDto> kafkaTemplate;

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

        User savedUser = userRepository.save(user);

        UserDto userDto = new UserDto().fromEntity(savedUser);
        userDto.setFirstName(registerDto.getUsername());
        userDto.setLastName(registerDto.getLastName());
        userDto.setPhone(registerDto.getPhone());
        kafkaTemplate.send("user-registration-topic", userDto);

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
}
