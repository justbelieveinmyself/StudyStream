package com.justbelieveinmyself.mailservice.kafka;

import com.justbelieveinmyself.library.dto.EnrollmentEvent;
import com.justbelieveinmyself.mailservice.dto.EmailVerificationDto;
import com.justbelieveinmyself.mailservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EnrollmentEventListener {
    private final EmailService emailService;

    @KafkaListener(topics = "user-enrollment-course", groupId = "group-id")
    public void handleEnrollmentEvent(EnrollmentEvent enrollmentEvent) {
        System.out.println("Received Enrollment event: " + enrollmentEvent);
        switch (enrollmentEvent.getEventType()) {
            case ENROLLMENT:
                emailService.sendEnrollment(enrollmentEvent);
                break;
            case DROP:
                emailService.sendDrop(enrollmentEvent);
                break;
            default:
                throw new NotImplementedException("Unknown event type: " + enrollmentEvent.getEventType());
        }
    }

}
