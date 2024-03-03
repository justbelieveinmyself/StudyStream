package com.justbelieveinmyself.authservice.domains.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LoginResponseDto {
    public String accessToken;
    public Instant accessTokenExpiration;
}
