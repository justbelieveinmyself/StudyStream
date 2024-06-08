package com.justbelieveinmyself.mailservice.kafka;

import com.justbelieveinmyself.library.dto.EmailUpdateDto;
import com.justbelieveinmyself.library.dto.EmailVerificationDto;
import com.justbelieveinmyself.mailservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class EmailListener {
    private final EmailService emailService;

    @KafkaListener(topics = "user-email-verify-topic", groupId = "email-verify-group-id", containerFactory = "emailVerificationContainerFactory")
    public void emailVerificationListener(EmailVerificationDto emailVerificationDto) {
        log.info("Verify email: " + emailVerificationDto.getEmail());
        emailService.sendVerification(emailVerificationDto);
    }

    @KafkaListener(topics = "user-email-update-topic", groupId = "email-update-group-id", containerFactory = "emailUpdateContainerFactory")
    public void emailUpdateListener(EmailUpdateDto emailUpdateDto) {
        log.info("Update email: " + emailUpdateDto.getEmail());
        emailService.updateEmail(emailUpdateDto);
    }

}
