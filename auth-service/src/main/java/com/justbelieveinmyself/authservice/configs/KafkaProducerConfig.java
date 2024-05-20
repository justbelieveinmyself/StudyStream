package com.justbelieveinmyself.authservice.configs;

import com.justbelieveinmyself.authservice.domains.dtos.UserDto;
import com.justbelieveinmyself.authservice.kafka.EmailUpdateDtoSerializer;
import com.justbelieveinmyself.authservice.kafka.EmailVerificationDtoSerializer;
import com.justbelieveinmyself.library.dto.EmailUpdateDto;
import com.justbelieveinmyself.library.dto.EmailVerificationDto;
import io.swagger.v3.core.util.Json;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class KafkaProducerConfig {
    @Value("${spring.kafka.producer.bootstrap-servers}")
    private List<String> bootstrapServers;

    @Bean
    public ProducerFactory<String, UserDto> userProducerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, UserDto> userKafkaTemplate() {
        return new KafkaTemplate<>(userProducerFactory());
    }

    @Bean
    public ProducerFactory<String, EmailVerificationDto> emailVerificationProducerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, EmailVerificationDtoSerializer.class);
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, EmailVerificationDto> emailVerificationKafkaTemplate() {
        return new KafkaTemplate<>(emailVerificationProducerFactory());
    }

    @Bean
    public ProducerFactory<String, EmailUpdateDto> emailUpdateProducerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);//TODO: FIX ALL AND DELETE CUSTOM DESERIALIZER AND SERIALIZER
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, EmailUpdateDtoSerializer.class);
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, EmailUpdateDto> emailUpdateKafkaTemplate() {
        return new KafkaTemplate<>(emailUpdateProducerFactory());
    }
}
