package com.justbelieveinmyself.authservice.services;

import com.justbelieveinmyself.authservice.domains.dtos.AccessToken;
import com.justbelieveinmyself.authservice.domains.dtos.RefreshRequestDto;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
        final String token = "token";
        RefreshRequestDto dto = RefreshRequestDto.builder().refreshToken(token).build();

        User user = User.builder()
                .id(1L)
                .username("user")
                .build();
        RefreshToken refreshToken = RefreshToken.builder()
                .id(1L)
                .token(token)
                .expiration(Instant.now().plus(10, ChronoUnit.HOURS))
                .user(user)
                .build();

        String accessTokenString = "accessToken";
        AccessToken accessToken = AccessToken.builder()
                .token(accessTokenString)
                .expiration(Instant.now().plus(2, ChronoUnit.HOURS)).build();


        when(refreshTokenRepository.findByToken(token)).thenReturn(Optional.of(refreshToken));
        when(jwtUtils.createAccessToken(user)).thenReturn(accessToken);

        RefreshResponseDto response = refreshTokenService.refreshToken(dto);

        assertEquals(response.getAccessToken(), accessTokenString);
        assertEquals(response.getRefreshToken(), token);

    }

}