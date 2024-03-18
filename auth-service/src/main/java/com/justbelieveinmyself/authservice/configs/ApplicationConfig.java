package com.justbelieveinmyself.authservice.configs;

import com.justbelieveinmyself.library.aspects.ValidationAspect;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

    @Bean
    public ValidationAspect validationAspect() {
        return new ValidationAspect();
    }
}
