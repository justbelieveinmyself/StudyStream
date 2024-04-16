package com.justbelieveinmyself.userservice.kafka;

import com.justbelieveinmyself.library.dto.EmailUpdateDto;
import com.justbelieveinmyself.userservice.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class EmailKafkaListener {
    private final UserService userService;

    @KafkaListener(topics = {"user-email-update-topic"}, groupId = "email-group-id", containerFactory = "emailKafkaListenerContainerFactory")
    public void listenEmailUpdateDto(EmailUpdateDto emailUpdateDto) {
        log.info("New message: " + emailUpdateDto.toString());
        userService.updateEmail(emailUpdateDto);
    }

}