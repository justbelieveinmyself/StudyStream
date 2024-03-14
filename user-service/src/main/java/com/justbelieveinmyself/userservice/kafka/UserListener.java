package com.justbelieveinmyself.userservice.kafka;

import com.justbelieveinmyself.userservice.domains.entities.User;
import com.justbelieveinmyself.userservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserListener {
    private final UserService userService;

    @KafkaListener(topics = {"user-registration-topic"}, groupId = "group-id")
    public void listenUserDto(User user) {
        System.out.println("New message: " + user.toString());
        userService.createNewUser(user);
    }

}
