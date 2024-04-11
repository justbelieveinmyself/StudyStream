package com.justbelieveinmyself.courseservice.controllers;

import com.justbelieveinmyself.courseservice.domains.dtos.EnrollmentDto;
import com.justbelieveinmyself.courseservice.domains.entities.Enrollment;
import com.justbelieveinmyself.courseservice.services.EnrollmentService;
import com.justbelieveinmyself.library.dto.ResponseMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/enrollments")
@CrossOrigin
@RequiredArgsConstructor
@Tag(name = "Enrollment API", description = "API for managing enrollments")
@SecurityRequirement(name = "Bearer Authentication")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @Operation(summary = "Enroll in the Course", description = "Enroll in the Course")
    @PostMapping("/{courseId}")
    public ResponseEntity<EnrollmentDto> enrollInCourse(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long courseId
    ) {
        EnrollmentDto responseDto = enrollmentService.enrollToCourse(courseId, userId);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @Operation(summary = "Drop from the Course", description = "Drop from the Course")
    @DeleteMapping("/{courseId}")
    public ResponseEntity<ResponseMessage> dropFromCourse(
            @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long courseId
    ) {
        enrollmentService.dropFromCourse(courseId, userId);
        return ResponseEntity.noContent().build();
    }

    //TODO: get all enrollments of course, enroll/drop user by id
}
