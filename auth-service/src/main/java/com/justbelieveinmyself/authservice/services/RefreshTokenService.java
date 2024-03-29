package com.justbelieveinmyself.authservice.services;

import com.justbelieveinmyself.authservice.domains.dtos.RefreshRequestDto;
import com.justbelieveinmyself.authservice.domains.dtos.RefreshResponseDto;
import com.justbelieveinmyself.authservice.domains.entities.RefreshToken;
import com.justbelieveinmyself.authservice.domains.entities.User;
import com.justbelieveinmyself.authservice.jwt.JwtUtils;
import com.justbelieveinmyself.authservice.repository.RefreshTokenRepository;
import com.justbelieveinmyself.library.exception.NotFoundException;
import com.justbelieveinmyself.library.exception.UnauthorizedException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtUtils jwtUtils;
    @Value("${jwt.refreshToken.expiration}")
    private Duration refreshTokenLifeTime;

    public RefreshResponseDto createRefreshToken(User user) {
        if (user.getRefreshToken() != null) {
            refreshTokenRepository.delete(user.getRefreshToken());
            user.setRefreshToken(null);
        }
        var accessToken = jwtUtils.createAccessToken(user);

        var refreshToken = refreshTokenRepository.save(RefreshToken.builder()
                .user(user)
                .token(UUID.randomUUID().toString())
                .expiration(ZonedDateTime.now(ZoneId.systemDefault()).plusMinutes(refreshTokenLifeTime.toMinutes()).toInstant())
                .build());
        return RefreshResponseDto.builder()
                .accessToken(accessToken.getToken())
                .accessTokenExpiration(accessToken.getExpiration())
                .refreshToken(refreshToken.getToken())
                .refreshTokenExpiration(refreshToken.getExpiration())
                .build();
    }

    public RefreshResponseDto refreshToken(RefreshRequestDto refreshRequestDto) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(refreshRequestDto.getRefreshToken())
                .orElseThrow(() -> new NotFoundException("Refresh token not found: " + refreshRequestDto.getRefreshToken()));
        if (refreshToken.isExpired()) {
            refreshTokenRepository.delete(refreshToken);
            throw new UnauthorizedException("Refresh token is expired: " + refreshRequestDto.getRefreshToken());
        }
        var accessToken = jwtUtils.createAccessToken(refreshToken.getUser());
        updateToken(refreshToken);
        return RefreshResponseDto.builder()
                .accessToken(accessToken.getToken())
                .accessTokenExpiration(accessToken.getExpiration())
                .refreshToken(refreshToken.getToken())
                .refreshTokenExpiration(refreshToken.getExpiration())
                .build();
    }

    private void updateToken(RefreshToken refreshToken) {
        refreshToken.setExpiration(ZonedDateTime.now(ZoneId.systemDefault()).plusMinutes(refreshTokenLifeTime.toMinutes()).toInstant());
        refreshTokenRepository.save(refreshToken);
    }

}
