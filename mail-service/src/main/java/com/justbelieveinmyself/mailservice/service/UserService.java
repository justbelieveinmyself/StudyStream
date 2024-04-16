package com.justbelieveinmyself.mailservice.service;

import com.justbelieveinmyself.library.exception.NotFoundException;
import com.justbelieveinmyself.mailservice.domains.entity.User;
import com.justbelieveinmyself.mailservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void updateEmail(Long userId, String email) {
        User user = findById(userId);
        user.setEmail(email);

        userRepository.save(user);
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User not found with id: " + userId));
    }

    public void saveUser(Long id, String username, String email) {
        User user = User.builder().id(id).username(username).email(email).build();
        userRepository.save(user);
    }

}
