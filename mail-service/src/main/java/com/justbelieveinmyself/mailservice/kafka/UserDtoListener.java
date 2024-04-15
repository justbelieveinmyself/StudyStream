package com.justbelieveinmyself.mailservice.kafka;

import com.justbelieveinmyself.library.dto.UserDto;
import com.justbelieveinmyself.mailservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDtoListener {
    private final UserService userService;

    @KafkaListener(topics = {"user-registration-topic"}, groupId = "group-id")
    public void listenUserDto(UserDto userDto) {
        System.out.println("New message: " + userDto.toString());
        userService.saveUser(userDto);
    }

}