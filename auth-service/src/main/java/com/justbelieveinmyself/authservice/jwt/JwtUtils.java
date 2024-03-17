package com.justbelieveinmyself.authservice.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.justbelieveinmyself.authservice.domains.dtos.AccessToken;
import com.justbelieveinmyself.authservice.domains.entities.User;
import com.justbelieveinmyself.library.enums.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Set;

@Component
public class JwtUtils {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration}")
    private Duration jwtLifeTime;

    public AccessToken createAccessToken(User user) {
        Instant expiresAt = ZonedDateTime.now(ZoneId.systemDefault()).plusMinutes(jwtLifeTime.toMinutes()).toInstant();
        String token = JWT.create()
                .withSubject(user.getUsername())
                .withClaim("userId", user.getId())
                .withArrayClaim("roles", user.getRoles().stream().map(Role::name).toArray(String[]::new))
                .withExpiresAt(expiresAt)
                .sign(Algorithm.HMAC256(secret));
        return new AccessToken(token, expiresAt);
    }
}