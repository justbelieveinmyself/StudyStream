package com.justbelieveinmyself.courseservice.services;

import com.justbelieveinmyself.courseservice.domains.dtos.EnrollmentDto;
import com.justbelieveinmyself.courseservice.domains.entities.Course;
import com.justbelieveinmyself.courseservice.domains.entities.Enrollment;
import com.justbelieveinmyself.courseservice.domains.enums.EnrollmentStatus;
import com.justbelieveinmyself.courseservice.repositories.EnrollmentRepository;
import com.justbelieveinmyself.library.dto.EnrollmentEvent;
import com.justbelieveinmyself.library.dto.EnrollmentEventType;
import com.justbelieveinmyself.library.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final CourseService courseService;
    private final UserService userService;
    private final MailService mailService;

    public EnrollmentDto enrollToCourse(Long courseId, Long userId) {
        Enrollment enrollment = new Enrollment();
        enrollment.setEnrollmentTime(Instant.now());
        enrollment.setStatus(EnrollmentStatus.ACTIVE);
        enrollment.setCourse(courseService.findById(courseId));
        enrollment.setUser(userService.findById(userId));

        Enrollment savedEnrollment = enrollmentRepository.save(enrollment);
        mailService.sendEnrollmentEvent(new EnrollmentEvent(userId, courseService.findById(courseId).getTitle(), EnrollmentEventType.ENROLLMENT));

        return new EnrollmentDto().fromEntity(savedEnrollment);
    }

    public void dropFromCourse(Long courseId, Long userId) {
        Enrollment enrollment = enrollmentRepository.findByCourse_IdAndUser_Id(courseId, userId)
                .orElseThrow(() -> new NotFoundException("User with ID: " + userId + " is not enrolled in a course with ID: " + courseId));

        enrollmentRepository.delete(enrollment);

        mailService.sendEnrollmentEvent(new EnrollmentEvent(userId, courseService.findById(courseId).getTitle(), EnrollmentEventType.DROP));
    }

    public Map<Long, List<Long>> getAllUsersOnCourse(Long courseId) {
        Map<Long, List<Long>> result = new HashMap<>();
        List<Enrollment> enrolls = enrollmentRepository.findByCourse_Id(courseId);
        result.put(courseId, enrolls.stream().map(enrollment -> enrollment.getUser().getId()).collect(Collectors.toList()));
        return result;
    }

    //TODO: logic with complete course, unlock new lessons
}
/*
{
 courseId: 10,
 userId: []
}

 */