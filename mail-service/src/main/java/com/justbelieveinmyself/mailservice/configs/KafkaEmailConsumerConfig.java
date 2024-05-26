package com.justbelieveinmyself.mailservice.configs;

import com.justbelieveinmyself.library.dto.EmailUpdateDto;
import com.justbelieveinmyself.mailservice.domains.dto.EmailVerificationDto;
import com.justbelieveinmyself.mailservice.kafka.deserializers.EmailUpdateDtoDeserializer;
import com.justbelieveinmyself.mailservice.kafka.deserializers.EmailVerificationDtoDeserializer;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaEmailConsumerConfig {
    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private final List<String> bootstrapServers;

    @Bean
    public ConsumerFactory<String, EmailVerificationDto> emailVerificationConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, EmailVerificationDtoDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, EmailVerificationDto>> emailVerificationContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, EmailVerificationDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(emailVerificationConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, EmailUpdateDto> emailUpdateConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, EmailUpdateDtoDeserializer.class);
        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean
    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, EmailUpdateDto>> emailUpdateContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, EmailUpdateDto> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(emailUpdateConsumerFactory());
        return factory;
    }

}
