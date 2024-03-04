package com.justbelieveinmyself.authservice.domains.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
@AllArgsConstructor
@Getter
public class AccessToken {
    private String token;
    private Instant expiration;
}
