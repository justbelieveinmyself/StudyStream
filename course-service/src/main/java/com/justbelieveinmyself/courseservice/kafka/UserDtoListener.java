package com.justbelieveinmyself.courseservice.kafka;

import com.justbelieveinmyself.courseservice.services.UserService;
import com.justbelieveinmyself.library.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class UserDtoListener {
    private final UserService userService;

    @KafkaListener(topics = {"user-registration-topic"}, groupId = "group-id")
    public void listenUserDto(UserDto userDto) {
        log.info("New message: " + userDto.toString());
        userService.saveUser(userDto);
    }

}
