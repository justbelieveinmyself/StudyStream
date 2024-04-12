package com.justbelieveinmyself.courseservice.controllers;

import com.justbelieveinmyself.courseservice.domains.dtos.EnrollmentDto;
import com.justbelieveinmyself.courseservice.services.EnrollmentService;
import com.justbelieveinmyself.library.aspects.RequiresRoleOrSelf;
import com.justbelieveinmyself.library.dto.ResponseMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/courses/{courseId}/enrollments/users")
@CrossOrigin
@RequiredArgsConstructor
@Tag(name = "Enrollment API", description = "API for managing enrollments")
@SecurityRequirement(name = "Bearer Authentication")
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @Operation(summary = "Enroll Current User", description = "This method allows the current user to enroll in a specific course")
    @PostMapping
    public ResponseEntity<EnrollmentDto> enrollCurrentUser(
            @Parameter(hidden = true) @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long courseId
    ) {
        EnrollmentDto responseDto = enrollmentService.enrollToCourse(courseId, userId);
        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @Operation(summary = "Withdraw Current User", description = "This method allows the current user to withdraw from a specific course")
    @DeleteMapping
    public ResponseEntity<ResponseMessage> withdrawCurrentUser(
            @Parameter(hidden = true) @RequestHeader("X-User-Id") Long userId,
            @PathVariable Long courseId
    ) {
        enrollmentService.dropFromCourse(courseId, userId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Enroll User by ID", description = "This method allows enrolling a user in a specific course by their user ID")
    @PostMapping("/{userId}")
    @RequiresRoleOrSelf(roles = "ADMIN")
    public ResponseEntity<EnrollmentDto> enrollUserById(
            @Parameter(hidden = true) @RequestHeader("X-User-Id") Long currentUserId,
            @Parameter(hidden = true) @RequestHeader("X-User-Roles") String[] currentUserRoles,
            @PathVariable Long userId,
            @PathVariable Long courseId
    ) {
        EnrollmentDto responseDto = enrollmentService.enrollToCourse(courseId, userId);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "Withdraw User by ID", description = "This method allows withdrawing a user from a specific course by their user ID")
    @DeleteMapping("/{userId}")
    @RequiresRoleOrSelf(roles = "ADMIN")
    public ResponseEntity<ResponseMessage> withdrawUserById(
            @Parameter(hidden = true) @RequestHeader("X-User-Id") Long currentUserId,
            @Parameter(hidden = true) @RequestHeader("X-User-Roles") String[] currentUserRoles,
            @PathVariable Long userId,
            @PathVariable Long courseId
    ) {
        enrollmentService.dropFromCourse(courseId, userId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get All Users on Course", description = "This method retrieves a list of all users enrolled in a specific course")
    @GetMapping
    public ResponseEntity<List<EnrollmentDto>> getAllUsersOnCourse(@PathVariable Long courseId) {
        List<EnrollmentDto> responseDto = enrollmentService.getAllUsersOnCourse(courseId);
        return ResponseEntity.ok(responseDto);
    }

}
