package com.justbelieveinmyself.userservice.services;

import com.justbelieveinmyself.library.exception.UsernameOrEmailAlreadyExistsException;
import com.justbelieveinmyself.userservice.domains.dtos.UserDto;
import com.justbelieveinmyself.userservice.domains.entities.User;
import com.justbelieveinmyself.userservice.domains.exceptions.UserNotFoundException;
import com.justbelieveinmyself.userservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public ResponseEntity<UserDto> getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with UserID: " + userId));
        return ResponseEntity.ok(new UserDto().fromEntity(user));
    }

    public String createNewUser(UserDto userDto) {
        User user = userDto.toEntity();
        if (userRepository.existsByUsernameOrEmail(user.getUsername(), user.getEmail())) {
            return "Bad";
        }
        userRepository.save(user);
        return "Ok";
    }
}

