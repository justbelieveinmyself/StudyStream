package com.justbelieveinmyself.userservice.kafka;

import com.justbelieveinmyself.library.dto.EmailUpdateDto;
import com.justbelieveinmyself.userservice.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailKafkaListener {
    private final UserService userService;

    @KafkaListener(topics = {"user-email-update-topic"}, groupId = "email-group-id", containerFactory = "emailKafkaListenerContainerFactory")
    public void listenEmailUpdateDto(EmailUpdateDto emailUpdateDto) {
        System.out.println("New message: " + emailUpdateDto.toString());
        userService.updateEmail(emailUpdateDto);
    }

}