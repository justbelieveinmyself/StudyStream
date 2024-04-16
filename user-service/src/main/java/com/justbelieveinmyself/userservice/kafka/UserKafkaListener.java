package com.justbelieveinmyself.userservice.kafka;

import com.justbelieveinmyself.userservice.domains.dtos.UserDto;
import com.justbelieveinmyself.userservice.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserKafkaListener {
    private final UserService userService;

    @KafkaListener(topics = {"user-registration-topic"}, groupId = "registration-group-id", containerFactory = "userKafkaListenerContainerFactory")
    public void listenUserDto(UserDto userDto) {
        log.info("New message: " + userDto.toString());
        userService.createNewUser(userDto);
    }

}
