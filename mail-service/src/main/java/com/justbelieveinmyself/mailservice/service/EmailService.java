package com.justbelieveinmyself.mailservice.service;

import com.justbelieveinmyself.mailservice.dto.EmailVerificationDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender sender;
    @Value("${spring.mail.username}")
    private String producerUsername;
    @Value("${site.hostname}")
    private String hostname;


    public void sendVerification(EmailVerificationDto emailVerificationDto) {
        String message = String.format("Hello, %s! \n" + "Welcome to StudySteam. Please, visit next link to verify email: %s/api/v1/auth/email?code=%s",
                emailVerificationDto.getUsername(),
                hostname,
                emailVerificationDto.getActivationCode()
        );

        send(emailVerificationDto.getEmail(), "Verification", message);
    }

    public void send(String consumerEmail, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(producerUsername);
        mailMessage.setTo(consumerEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        sender.send(mailMessage);
    }
}
