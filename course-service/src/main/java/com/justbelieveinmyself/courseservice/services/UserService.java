package com.justbelieveinmyself.courseservice.services;

import com.justbelieveinmyself.courseservice.domains.entities.User;
import com.justbelieveinmyself.courseservice.repositories.UserRepository;
import com.justbelieveinmyself.library.dto.UserDto;
import com.justbelieveinmyself.library.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found with UserID: " + userId));
    }

    public void saveUser(Long userId) {
        User user = User.builder().id(userId).build();
        userRepository.save(user);
    }

}
