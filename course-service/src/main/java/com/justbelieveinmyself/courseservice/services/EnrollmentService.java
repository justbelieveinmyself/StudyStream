package com.justbelieveinmyself.courseservice.services;

import com.justbelieveinmyself.courseservice.domains.dtos.EnrollmentDto;
import com.justbelieveinmyself.courseservice.repositories.EnrollmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;

    public EnrollmentDto enrollToCourse(Long courseId, Long userId) {
        return null;
    }//TODO: implement

    public void dropFromCourse(Long courseId, Long userId) {

    }

    public List<EnrollmentDto> getAllUsersOnCourse(Long courseId) {
        return null;
    }
}
