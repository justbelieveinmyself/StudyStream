package com.justbelieveinmyself.mailservice.service;

import com.justbelieveinmyself.library.dto.EmailUpdateDto;
import com.justbelieveinmyself.library.dto.EmailVerificationDto;
import com.justbelieveinmyself.mailservice.domains.entity.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmailServiceTest {
    @InjectMocks
    private EmailService emailService;
    @Mock
    private JavaMailSender sender;
    @Mock
    private UserService userService;

    @Test
    void sendVerification() {
        EmailVerificationDto dto = EmailVerificationDto.builder()
                .username("user")
                .email("my@mail.com")
                .activationCode("somecode").build();

        emailService.sendVerification(dto);

        verify(sender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void sendEnrollmentEvent() {
        final Long userId = 1L;
        final String courseTitle = "My Course Title";

        when(userService.findById(userId)).thenReturn(new User(userId, "some@mail.com", "user"));

        emailService.sendEnrollmentEvent(userId, courseTitle);

        verify(userService, times(1)).findById(userId);
        verify(sender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void sendDropEvent() {
        final Long userId = 1L;
        final String courseTitle = "My Course Title";

        when(userService.findById(userId)).thenReturn(new User(userId, "some@mail.com", "user"));

        emailService.sendDropEvent(userId, courseTitle);

        verify(userService, times(1)).findById(userId);
        verify(sender, times(1)).send(any(SimpleMailMessage.class));
    }

    @Test
    void updateEmail() {
        final Long userId = 1L;
        final String email = "my@mail.com";
        EmailUpdateDto dto = EmailUpdateDto.builder()
                .userId(userId)
                .email(email)
                .build();

        emailService.updateEmail(dto);

        verify(userService, times(1)).updateEmail(userId, email);
    }

}