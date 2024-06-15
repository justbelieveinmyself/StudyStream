package com.justbelieveinmyself.authservice.services;

import com.justbelieveinmyself.authservice.apiclients.UserServiceClient;
import com.justbelieveinmyself.authservice.domains.dtos.EmailDto;
import com.justbelieveinmyself.authservice.domains.entities.User;
import com.justbelieveinmyself.authservice.repository.UserRepository;
import com.justbelieveinmyself.library.dto.EmailUpdateDto;
import com.justbelieveinmyself.library.dto.EmailVerificationDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class EmailServiceTest {
    @InjectMocks
    private EmailService emailService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserServiceClient userServiceClient;
    @Mock
    private KafkaTemplate<String, EmailVerificationDto> emailVerificationKafkaTemplate;
    @Mock
    private KafkaTemplate<String, EmailUpdateDto> emailUpdateKafkaTemplate;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void sendActivationCode() throws InterruptedException {
        final String username = "user";
        final String email = "user@mail.com";
        final String activationCode = "activationCode";
        final String topicName = "user-email-verify-topic";

        EmailVerificationDto expectedDto = EmailVerificationDto.builder()
                .username(username)
                .email(email)
                .activationCode(activationCode).build();


        emailService.sendActivationCode(username, email, activationCode);


//        TODO: verify(emailVerificationKafkaTemplate, times(1)).send(eq(topicName), any(EmailVerificationDto.class));
    }

    @Test
    void updateEmail() {
        final Long userId = 1L;
        final String newEmail = "newEmail";
        final String topic = "user-email-update-topic";
        EmailDto emailDto = new EmailDto();
        emailDto.setEmail(newEmail);

        User user = User.builder()
                .id(userId)
                .email("old")
                .username("user").build();

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(userRepository.existsByEmail(newEmail)).thenReturn(false);

        emailService.updateEmail(userId, emailDto);


//      TODO:  verify(emailUpdateKafkaTemplate, times(1)).send(eq(topic), any(EmailUpdateDto.class));
        verify(userRepository, times(1)).existsByEmail(newEmail);
        verify(userRepository, times(1)).findById(userId);
        verify(userRepository, times(1)).save(any(User.class));
        verify(userServiceClient, times(1)).updateEmail(any(EmailUpdateDto.class));
    }

    @Test
    void verifyEmail() {
        final String activationCode = "activationCode";
        User user = User.builder()
                .id(1L)
                .username("user")
                .activationCode(activationCode)
                .email("user@mail.com").build();

        when(userRepository.findByActivationCode(activationCode)).thenReturn(Optional.of(user));

        emailService.verifyEmail(activationCode);
        try {
            Thread.currentThread().sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        verify(userRepository, times(1)).findByActivationCode(activationCode);
        verify(userRepository, times(1)).save(user);
        assertNull(user.getActivationCode());
    }
}