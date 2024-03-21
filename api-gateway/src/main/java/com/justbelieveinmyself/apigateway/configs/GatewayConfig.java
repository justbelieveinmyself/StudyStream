package com.justbelieveinmyself.apigateway.configs;

import com.justbelieveinmyself.apigateway.filters.AuthenticationFilter;
import org.springdoc.core.properties.SpringDocConfigProperties;
import org.springdoc.core.properties.SwaggerUiConfigParameters;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

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
                .route("user-service", r -> r.path("/api/v1/user/**")
                        .filters(f -> f.filter(authFilter))
                        .uri("lb://user-service"))
                .route("user-service-no-wildcard", r -> r.path("/api/v1/user")
                        .filters(f -> f.filter(authFilter))
                        .uri("lb://user-service"))
                .route("swagger-redirect", r -> r.path("/v3/api-docs/**")
                        .filters(f -> f.rewritePath("/v3/api-docs/(?<name>.*)", "/${name}/v3/api-docs"))
                        .uri("lb://api-gateway")
                ).build();
    }

}
