package com.justbelieveinmyself.userservice.services;

import com.justbelieveinmyself.userservice.domains.dtos.UpdateUserDto;
import com.justbelieveinmyself.userservice.domains.dtos.UserDto;
import com.justbelieveinmyself.userservice.domains.entities.User;
import com.justbelieveinmyself.userservice.domains.exceptions.UserNotFoundException;
import com.justbelieveinmyself.userservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserDto getUserById(Long userId) {
        User user = findById(userId);
        return new UserDto().fromEntity(user);
    }

    public void createNewUser(User user) {
        userRepository.save(user);
    }

    public UserDto updateUserById(Long userId, UpdateUserDto dto) {
        User user = findById(userId);
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhone(dto.getPhone());
        User savedUser = userRepository.save(user);
        return new UserDto().fromEntity(savedUser);
    }

    public UserDto getUserByUsername(String username) {
        User user = findByUsername(username);
        return new UserDto().fromEntity(user);
    }

    public UserDto updateUserByUsername(String username, UpdateUserDto dto) {
        User user = findByUsername(username);
        user.setFirstName(dto.getFirstName()); // id, username - cannot change; email - by patch with verification; roles - by user with authorities
        user.setLastName(dto.getLastName());
        user.setPhone(dto.getPhone());
        User savedUser = userRepository.save(user);
        return new UserDto().fromEntity(savedUser);
    }

    public void deleteUserByUsername(String username) {
        User user = findByUsername(username);
        userRepository.delete(user);
    }

    public UserDto patchUserByUsername(String username, UpdateUserDto dto) {
        User user = findByUsername(username);
        if (dto.getFirstName() != null) {
            user.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            user.setLastName(dto.getLastName());
        }
        if (dto.getPhone() != null) {
            user.setPhone(user.getPhone());
        }
        User savedUser = userRepository.save(user);
        return new UserDto().fromEntity(savedUser);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFoundException("User not found with Username: " + username));
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found with UserID: " + id));
    }

}

