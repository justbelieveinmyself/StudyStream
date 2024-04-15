package com.justbelieveinmyself.mailservice.kafka;

import com.justbelieveinmyself.library.dto.EmailUpdateDto;
import com.justbelieveinmyself.mailservice.domains.dto.EmailVerificationDto;
import com.justbelieveinmyself.mailservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailListener {
    private final EmailService emailService;

    @KafkaListener(topics = "user-email-verify-topic", groupId = "group-id")
    public void emailVerificationListener(EmailVerificationDto emailVerificationDto) {
        System.out.println("Verify email: " + emailVerificationDto.getEmail());
        emailService.sendVerification(emailVerificationDto);
    }

    @KafkaListener(topics = "user-email-update-topic", groupId = "group-id")
    public void emailUpdateListener(EmailUpdateDto emailUpdateDto) {
        System.out.println("Update email: " + emailUpdateDto.getEmail());
        emailService.updateEmail(emailUpdateDto);
    }

}
