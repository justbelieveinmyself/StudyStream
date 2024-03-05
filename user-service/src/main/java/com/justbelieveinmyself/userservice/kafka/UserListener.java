package com.justbelieveinmyself.userservice.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class UserListener {
    @KafkaListener(topics = {"user-registration-topic"}, groupId = "group-id")
    void listenUsername(String message) {
        System.out.println("New message:" + message);
    }
}
