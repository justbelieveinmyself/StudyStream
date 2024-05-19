package com.justbelieveinmyself.courseservice.configs;

import com.justbelieveinmyself.courseservice.kafka.EnrollmentEventSerializer;
import com.justbelieveinmyself.library.dto.EnrollmentEvent;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;

import java.util.HashMap;
import java.util.List;

@Configuration
@RequiredArgsConstructor
public class KafkaMailProducerConfig {
    @Value("${spring.kafka.consumer.bootstrap-servers}")
    private final List<String> bootstrapServers;

    @Bean
    public ProducerFactory<String, EnrollmentEvent> producerFactory() {
        HashMap<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, EnrollmentEventSerializer.class);
        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<String, EnrollmentEvent> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public NewTopic enrollmentTopic() {
        return TopicBuilder.name("user-enrollment-course").build();
    }

}
