package com.justbelieveinmyself.mailservice.kafka;

import com.justbelieveinmyself.library.dto.EnrollmentEvent;
import com.justbelieveinmyself.mailservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Log4j2
public class EnrollmentEventListener {
    private final EmailService emailService;

    @KafkaListener(topics = "user-enrollment-course", groupId = "enrollment-group-id", containerFactory = "enrollmentContainerFactory")
    public void handleEnrollmentEvent(EnrollmentEvent enrollmentEvent) {
        log.info("Received Enrollment event: " + enrollmentEvent);
        switch (enrollmentEvent.getEventType()) {
            case ENROLLMENT:
                emailService.sendEnrollmentEvent(enrollmentEvent.getUserId(), enrollmentEvent.getCourseTitle());
                break;
            case DROP:
                emailService.sendDropEvent(enrollmentEvent.getUserId(), enrollmentEvent.getCourseTitle());
                break;
            default:
                throw new NotImplementedException("Unknown event type: " + enrollmentEvent.getEventType());
        }
    }

}
