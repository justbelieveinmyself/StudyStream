package com.justbelieveinmyself.authservice.services;

import com.justbelieveinmyself.authservice.domains.dtos.*;
import com.justbelieveinmyself.authservice.domains.entities.User;
import com.justbelieveinmyself.authservice.repository.UserRepository;
import com.justbelieveinmyself.library.enums.Role;
import com.justbelieveinmyself.library.exception.UsernameOrEmailAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.header.internals.RecordHeader;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.requestreply.RequestReplyFuture;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.kafka.support.SendResult;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ExecutionException;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;
    private final ReplyingKafkaTemplate<String, UserDto, String> kafkaTemplate;

    public UserDto register(RegisterDto registerDto) {
        if (userRepository.existsByUsernameOrEmail(registerDto.getUsername(), registerDto.getEmail())) {
            throw new UsernameOrEmailAlreadyExistsException("User with username or email already exists!");
        }
        try {
            User user = new User();
            user.setUsername(registerDto.getUsername());
            user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
            user.setEmail(registerDto.getEmail());
            user.setRoles(Set.of(Role.USER));
            UserDto userDto = new UserDto().fromEntity(user);
            ProducerRecord<String, UserDto> record = new ProducerRecord<String, UserDto>("user-registration-topic", userDto);
            record.headers().add(new RecordHeader(KafkaHeaders.REPLY_TOPIC, "user-registration-reply-topic".getBytes()));
            RequestReplyFuture<String, UserDto, String> future = kafkaTemplate.sendAndReceive(record);
            SendResult<String, UserDto> sendResult = future.getSendFuture().get();
            sendResult.getProducerRecord().headers().forEach(header -> System.out.println(header.key() + ": " + header.value().toString()));
            ConsumerRecord<String, String> consumerRecord = future.get();
            if (consumerRecord.value().equals("Ok")) {
                User savedUser = userRepository.save(user);
                return new UserDto().fromEntity(savedUser);
            }
            throw new UsernameOrEmailAlreadyExistsException("Cannot create user!: " + consumerRecord.value());
        } catch (InterruptedException | ExecutionException e) {
            throw new UsernameOrEmailAlreadyExistsException("Error sending data");
        }
    }

    public RefreshResponseDto login(LoginRequestDto loginRequestDto) {
        authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequestDto.getUsername(), loginRequestDto.getPassword()));
        User user = userRepository.findByUsername(loginRequestDto.getUsername()).get();
        RefreshResponseDto refreshResponseDto = refreshTokenService.createRefreshToken(user);
        return refreshResponseDto;
    }

    public RefreshResponseDto refreshToken(RefreshRequestDto refreshRequestDto) {
        return refreshTokenService.refreshToken(refreshRequestDto);
    }
}
