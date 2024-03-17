package com.justbelieveinmyself.authservice.configs;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic userRegistrationTopic() {
        return TopicBuilder.name("user-registration-topic").build();
    }

    @Bean
    public NewTopic emailVerificationTopic() {
        return TopicBuilder.name("user-email-verify-topic").build();
    }

    @Bean
    public NewTopic emailUpdateTopic() {
        return TopicBuilder.name("user-email-update-topic").build();
    }

}
