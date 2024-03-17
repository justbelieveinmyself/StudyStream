package com.justbelieveinmyself.apigateway.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.server.reactive.ServerHttpRequest;

import java.util.List;
import java.util.function.Predicate;

@Configuration
public class RouterValidator {

    public static final List<String> openEndpoints = List.of(
            "api/v1/auth/login",
            "api/v1/auth/register",
            "api/v1/auth/refresh",
            "api/v1/email"
    );

    public Predicate<ServerHttpRequest> isSecured = request -> {
        String method = request.getMethod().toString();
        String path = request.getURI().getPath();
        boolean isOpenEndpoint = openEndpoints.stream().anyMatch(path::contains);
        boolean isPostOrGet = method.equals(HttpMethod.POST.name()) || method.equals(HttpMethod.GET.name());
        return !isOpenEndpoint || !isPostOrGet;
    };




}
