package com.justbelieveinmyself.mailservice.kafka;

import com.justbelieveinmyself.library.dto.UserDto;
import com.justbelieveinmyself.mailservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserDtoListener {
    private final UserService userService;

    @KafkaListener(topics = "user-registration-topic", groupId = "registration-group-id-mail", containerFactory = "userContainerFactory")
    public void listenUserDto(UserDto userDto) {
        log.info("New message: " + userDto.toString());
        userService.saveUser(userDto.getId(), userDto.getUsername(), userDto.getEmail());
    }

}