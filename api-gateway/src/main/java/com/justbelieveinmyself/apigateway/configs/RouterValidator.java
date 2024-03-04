package com.justbelieveinmyself.apigateway.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.List;
import java.util.function.Predicate;

@Configuration
public class RouterValidator {

    public static final List<String> openEndpoints = List.of(
            "api/v1/auth/login",
            "api/v1/auth/register",
            "api/v1/auth/refresh"
    );

    public Predicate<ServerHttpRequest> isSecured = request -> openEndpoints.stream()
            .noneMatch(uri -> request.getURI().getPath().contains(uri));

}
