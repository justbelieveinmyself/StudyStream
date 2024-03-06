package com.justbelieveinmyself.userservice.kafka;

import com.justbelieveinmyself.userservice.domains.dtos.UserDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserListener {
    @KafkaListener(topics = {"user-registration-topic"}, groupId = "group-id", containerFactory = "userKafkaListenerContainerFactory")
    void listenUsername(UserDto message) {
        System.out.println("New message: " + message.toString());
    }
}
