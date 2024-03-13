package com.justbelieveinmyself.courseservice.kafka;

import com.justbelieveinmyself.courseservice.domains.dtos.UserDto;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Service
public class UserDtoListener {
    @KafkaListener(topics = {"user-registration-topic"}, groupId = "group-id")
    public void listenUserDto(UserDto userDto) {
        System.out.println("New message: " + userDto.toString());
    }
}
