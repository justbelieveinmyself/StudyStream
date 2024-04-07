package com.justbelieveinmyself.courseservice.controllers;

import com.justbelieveinmyself.courseservice.domains.dtos.CourseDto;
import com.justbelieveinmyself.courseservice.domains.dtos.update.UpdateCourseDto;
import com.justbelieveinmyself.library.aspects.ValidateErrors;
import com.justbelieveinmyself.library.dto.ResponseMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Course API", description = "Create, get, update and delete courses")
@SecurityRequirement(name = "Bearer Authentication")
public interface CourseController {

    @Operation(summary = "Get Course by ID", description = "Get Course by ID")
    @GetMapping("/{courseId}")
    ResponseEntity<CourseDto> getCourseById(@PathVariable Long courseId);

    @Operation(summary = "Create Course", description = "Create Course")
    @PostMapping
    @ValidateErrors
    ResponseEntity<CourseDto> createNewCourse(
            @RequestBody @Valid CourseDto courseDto,
            BindingResult result,
            @Parameter(hidden = true) @RequestHeader("X-User-Id") Long userId
    );

    @Operation(summary = "Delete Course by ID", description = "Delete Course by ID")
    @DeleteMapping("/{courseId}")
    ResponseEntity<ResponseMessage> deleteCourseById(
            @PathVariable Long courseId,
            @Parameter(hidden = true) @RequestHeader("X-User-Id") Long userId
    );

    @Operation(summary = "Update Course by ID", description = "Update Course by ID")
    @PutMapping("/{courseId}")
    @ValidateErrors
    ResponseEntity<CourseDto> updateCourseById(
            @PathVariable Long courseId,
            @Parameter(hidden = true) @RequestHeader("X-User-Id") Long userId,
            @RequestBody @Valid UpdateCourseDto updateCourseDto,
            BindingResult result
    );

    @Operation(summary = "Partial Update Course by ID", description = "Partial Update Course by ID")
    @PatchMapping("/{courseId}")
    ResponseEntity<CourseDto> patchCourseById(
            @PathVariable Long courseId,
            @Parameter(hidden = true) @RequestHeader("X-User-Id") Long userId,
            @RequestBody UpdateCourseDto updateCourseDto,
            BindingResult result
    );

}
