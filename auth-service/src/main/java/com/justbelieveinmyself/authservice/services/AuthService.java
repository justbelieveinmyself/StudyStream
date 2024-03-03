package com.justbelieveinmyself.authservice.services;

import com.justbelieveinmyself.authservice.domains.dtos.LoginRequestDto;
import com.justbelieveinmyself.authservice.domains.dtos.LoginResponseDto;
import com.justbelieveinmyself.authservice.domains.dtos.RegisterDto;
import com.justbelieveinmyself.authservice.domains.dtos.UserDto;
import com.justbelieveinmyself.authservice.domains.entities.User;
import com.justbelieveinmyself.authservice.exceptions.UsernameOrEmailAlreadyExistsException;
import com.justbelieveinmyself.authservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;

    public UserDto register(RegisterDto registerDto) {
        if (userRepository.existsByUsernameOrEmail(registerDto.getUsername(), registerDto.getEmail())) {
            throw new UsernameOrEmailAlreadyExistsException("User with username or email already exists!");
        }
        User user = new User();
        user.setUsername(registerDto.getUsername());
        user.setPassword(registerDto.getPassword());
        User savedUser = userRepository.save(user);
        return new UserDto().fromEntity(savedUser);
    }

    public LoginResponseDto login(LoginRequestDto loginRequestDto) {
        throw new NotImplementedException();
    }
}
