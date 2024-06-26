package com.justbelieveinmyself.apigateway.configs;

import com.justbelieveinmyself.apigateway.filters.AuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfig {
    private final AuthenticationFilter authFilter;

    public GatewayConfig(AuthenticationFilter authFilter) {
        this.authFilter = authFilter;
    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("auth-service", r -> r.path("/api/v1/auth/**")
                        .filters(f -> f.filter(authFilter))
                        .uri("lb://auth-service"))
                .route("auth-service-email", r -> r.path("/api/v1/email")
                        .filters(f -> f.filter(authFilter))
                        .uri("lb://auth-service"))
                .route("user-service", r -> r.path("/api/v1/user/**", "/api/v1/user")
                        .filters(f -> f.filter(authFilter))
                        .uri("lb://user-service"))
                .route("user-service", r -> r.path("/api/v1/users/**", "/api/v1/users")
                        .filters(f -> f.filter(authFilter))
                        .uri("lb://user-service"))
                .route("course-service", r -> r.path("/api/v1/courses/**", "/api/v1/courses")
                        .filters(f -> f.filter(authFilter))
                        .uri("lb://course-service"))
                .route("swagger-redirect", r -> r.path("/v3/api-docs/**")
                        .filters(f -> f.rewritePath("/v3/api-docs/(?<name>.*)", "/${name}/v3/api-docs"))
                        .uri("lb://api-gateway")
                ).build();
    }

}
