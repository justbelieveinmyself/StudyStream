package com.justbelieveinmyself.userservice.kafka;

import com.justbelieveinmyself.userservice.domains.dtos.UserDto;
import com.justbelieveinmyself.userservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserKafkaListener {
    private final UserService userService;

    @KafkaListener(topics = {"user-registration-topic"}, groupId = "group-id", containerFactory = "userKafkaListenerContainerFactory")
    public void listenUserDto(UserDto userDto) {
        System.out.println("New message: " + userDto.toString());
        userService.createNewUser(userDto);
    }

}
