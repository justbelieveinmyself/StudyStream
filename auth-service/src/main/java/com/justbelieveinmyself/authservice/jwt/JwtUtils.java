package com.justbelieveinmyself.authservice.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.justbelieveinmyself.authservice.domains.dtos.AccessToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

@Component
public class JwtUtils {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Duration jwtLifeTime;

    public AccessToken createAccessToken(String username) {
        Instant expiresAt = ZonedDateTime.now(ZoneId.systemDefault()).plusMinutes(jwtLifeTime.toMinutes()).toInstant();
        String token = JWT.create()
                .withSubject(username)
                .withExpiresAt(expiresAt)
                .sign(Algorithm.HMAC256(secret));
        return new AccessToken(token, expiresAt);
    }
}
