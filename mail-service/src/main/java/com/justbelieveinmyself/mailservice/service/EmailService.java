package com.justbelieveinmyself.mailservice.service;

import com.justbelieveinmyself.library.dto.EmailUpdateDto;
import com.justbelieveinmyself.library.dto.EmailVerificationDto;
import com.justbelieveinmyself.mailservice.domains.entity.User;
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
    private final UserService userService;


    public void sendVerification(EmailVerificationDto emailVerificationDto) {
        String message = String.format(
                "Hello, %s, \n\n" +
                        "Welcome to StudySteam. Please, visit this link below to finish verification:\n\n%s/api/v1/email?code=%s\n\n" +
                        "If you did not request this, please REPLY IMMEDIATELY as your account may be in danger.\n\n" +
                "--\nStudyStream | %s",
                emailVerificationDto.getUsername(),
                hostname,
                emailVerificationDto.getActivationCode(),
                hostname
        );

        send(emailVerificationDto.getEmail(), "StudySteam`account verification", message);
    }

    private void send(String consumerEmail, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(producerUsername);
        mailMessage.setTo(consumerEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        sender.send(mailMessage);
    }

    private void send(User consumerUser, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(producerUsername);
        mailMessage.setTo(consumerUser.getEmail());
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        sender.send(mailMessage);
    }

    public void sendEnrollmentEvent(Long userId, String courseTitle) {
        User user = userService.findById(userId);

        String subject = "You are now enrolled in the course: " + courseTitle;
        String message = String.format(
                "Dear %s,\n\nYou have successfully enrolled in the course: %s.\n\nThank you!",
                user.getUsername(), courseTitle);

        send(user, subject, message);
    }

    public void sendDropEvent(Long userId, String courseTitle) {
        User user = userService.findById(userId);

        String subject = "You have been dropped from the course: " + courseTitle;
        String message = String.format(
                "Dear %s,\n\nYou have been dropped from the course: %s.\n\nThank you!",
                user.getUsername(), courseTitle);

        send(user, subject, message);
    }

    public void updateEmail(EmailUpdateDto emailUpdateDto) {
        userService.updateEmail(emailUpdateDto.getUserId(), emailUpdateDto.getEmail());
    }

}
