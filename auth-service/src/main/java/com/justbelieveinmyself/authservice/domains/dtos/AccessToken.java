package com.justbelieveinmyself.authservice.domains.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
@AllArgsConstructor
@Builder
@Getter
public class AccessToken {
    private String token;
    private Instant expiration;
}
