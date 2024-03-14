package com.justbelieveinmyself.courseservice.kafka;

import com.justbelieveinmyself.courseservice.domains.dtos.UserDto;
import com.justbelieveinmyself.courseservice.domains.entities.User;
import com.justbelieveinmyself.courseservice.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDtoListener {
    private final UserRepository userRepository;

    @KafkaListener(topics = {"user-registration-topic"}, groupId = "group-id")
    public void listenUserDto(UserDto userDto) {
        System.out.println("New message: " + userDto.toString());
        saveUser(userDto);
    }

    public void saveUser(UserDto userDto) {
        User user = User.builder().id(userDto.getId()).build();
        userRepository.save(user);
    }

}
