package com.justbelieveinmyself.authservice.services;

import com.justbelieveinmyself.authservice.domains.dtos.RefreshResponseDto;
import com.justbelieveinmyself.authservice.domains.entities.RefreshToken;
import com.justbelieveinmyself.authservice.domains.entities.User;
import com.justbelieveinmyself.authservice.jwt.JwtUtils;
import com.justbelieveinmyself.authservice.repository.RefreshTokenRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RefreshTokenServiceTest {
    @InjectMocks
    private RefreshTokenService refreshTokenService;
    @Mock
    private RefreshTokenRepository refreshTokenRepository;
    @Mock
    private JwtUtils jwtUtils;

    @Test
    void createRefreshToken() {
        RefreshToken refreshToken = RefreshToken.builder()
                .id(1L)
                .expiration(Instant.now().plus(10, ChronoUnit.HOURS))
                .token("token").build();

        User user = User.builder()
                .id(1L)
                .refreshToken(refreshToken).build();

        when(refreshTokenRepository.save(any()))
                .thenAnswer(i -> i.getArgument(0));

        RefreshResponseDto refresh = refreshTokenService.createRefreshToken(user);

        verify(jwtUtils, times(1)).createAccessToken(user);
        verify(refreshTokenRepository, times(1)).save(any());

    }

    @Test
    void refreshToken() {
    }

}