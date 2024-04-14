package com.justbelieveinmyself.courseservice.services;

import com.justbelieveinmyself.library.dto.EnrollmentEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailService {
    private final KafkaTemplate<String, EnrollmentEvent> kafkaTemplate;

    public void sendEnrollmentEvent(EnrollmentEvent enrollmentEvent) {
        kafkaTemplate.send("user-enrollment-course", enrollmentEvent);
    }

}
