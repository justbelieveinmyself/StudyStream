package com.justbelieveinmyself.authservice.domains.dtos;

import lombok.*;

import java.time.Instant;

@Builder
@Getter
public class RefreshResponseDto {
    private String accessToken;
    private Instant accessTokenExpiration;
    private String refreshToken;
    private Instant refreshTokenExpiration;
}
