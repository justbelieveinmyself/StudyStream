package com.justbelieveinmyself.userservice.services;

import com.justbelieveinmyself.library.dto.ResponseMessage;
import com.justbelieveinmyself.userservice.domains.dtos.UpdateUserDto;
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

    public void createNewUser(User user) {
        userRepository.save(user);
    }

    public ResponseEntity<UserDto> updateUserById(Long userId, UpdateUserDto dto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with UserID: " + userId));
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhone(dto.getPhone());
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(new UserDto().fromEntity(savedUser));
    }

    public ResponseEntity<UserDto> getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with Username: " + username));
        return ResponseEntity.ok(new UserDto().fromEntity(user));
    }

    public ResponseEntity<UserDto> updateUserByUsername(String username, UpdateUserDto dto) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with Username: " + username));
        user.setFirstName(dto.getFirstName()); //id, username - cannot change; email - by patch with verification; roles - by user with authorities
        user.setLastName(dto.getLastName());
        user.setPhone(dto.getPhone());
        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(new UserDto().fromEntity(savedUser));
    }

    public ResponseEntity<ResponseMessage> deleteUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found with Username: " + username));
        userRepository.delete(user);
        return ResponseEntity.ok(new ResponseMessage(200, "User successfully deleted with username: " + username));
    }
}

