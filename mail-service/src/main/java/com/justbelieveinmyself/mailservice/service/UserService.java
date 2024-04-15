package com.justbelieveinmyself.mailservice.service;

import com.justbelieveinmyself.library.dto.UserDto;
import com.justbelieveinmyself.library.exception.NotFoundException;
import com.justbelieveinmyself.mailservice.domains.entity.User;
import com.justbelieveinmyself.mailservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void saveUser(UserDto userDto) {
        User user = User.builder().id(userDto.getId()).email(userDto.getEmail()).build();
        userRepository.save(user);
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found with id: " + userId));
    }

}
