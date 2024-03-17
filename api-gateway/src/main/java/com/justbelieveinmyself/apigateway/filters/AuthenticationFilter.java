package com.justbelieveinmyself.apigateway.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.justbelieveinmyself.apigateway.configs.RouterValidator;
import com.justbelieveinmyself.apigateway.exceptions.UnauthorizedException;
import org.apache.http.HttpHeaders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter implements GatewayFilter {
    private static final String TOKEN_PREFIX = "Bearer ";
    @Value("${jwt.secret}")
    private String secret;
    private final RouterValidator validator;

    public AuthenticationFilter(RouterValidator validator) {
        this.validator = validator;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        if (validator.isSecured.test(request)) {

            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {
                throw new UnauthorizedException("Authorization header is empty!");
            }
            try {
                final String bearerToken = request.getHeaders().getOrEmpty(HttpHeaders.AUTHORIZATION).get(0);
                String token = bearerToken.replace(TOKEN_PREFIX, "");
                DecodedJWT decodedJWT = verifyAndDecodeJWT(token);
                String username = decodedJWT.getSubject();
                Long userId = decodedJWT.getClaim("userId").asLong();
                String[] roles = decodedJWT.getClaim("roles").as(String[].class);

                ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                        .header("X-Username", username)
                        .header("X-User-Id", userId.toString())
                        .header("X-User-Roles", roles).build();

                return chain.filter(exchange.mutate().request(modifiedRequest).build());
            } catch (TokenExpiredException e) {
                throw new UnauthorizedException("Token expired");
            } catch (JWTDecodeException e) {
                throw new UnauthorizedException("Token invalid format");
            }

        }
        return chain.filter(exchange);
    }

    private DecodedJWT verifyAndDecodeJWT(String accessToken) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret)).build();
        return verifier.verify(accessToken);
    }
}
