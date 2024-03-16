package com.justbelieveinmyself.mailservice.kafka;

import com.justbelieveinmyself.mailservice.dto.EmailVerificationDto;
import com.justbelieveinmyself.mailservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailListener {
    private final EmailService emailService;

    @KafkaListener(topics = "user-email-verify-topic", groupId = "group-id")
    public void verifyEmail(EmailVerificationDto emailVerificationDto) {
        System.out.println("Verify email: " + emailVerificationDto.getEmail());
        emailService.sendVerification(emailVerificationDto);
    }

}
