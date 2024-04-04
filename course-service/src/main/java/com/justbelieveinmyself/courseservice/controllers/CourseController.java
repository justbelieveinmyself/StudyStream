package com.justbelieveinmyself.courseservice.controllers;

import com.justbelieveinmyself.courseservice.domains.dtos.CourseDto;
import com.justbelieveinmyself.courseservice.domains.dtos.update.UpdateCourseDto;
import com.justbelieveinmyself.courseservice.services.CourseService;
import com.justbelieveinmyself.library.aspects.ValidateErrors;
import com.justbelieveinmyself.library.dto.ResponseMessage;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/course")
@CrossOrigin
@RequiredArgsConstructor
@Tag(name = "Course API", description = "Create, get, update and delete courses")
@SecurityRequirement(name = "Bearer Authentication")
public class CourseController {
    private final CourseService courseService;

    @Operation(summary = "Get Course by ID", description = "Get Course by ID")
    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDto> getCourseById(@PathVariable Long courseId) {
        CourseDto courseDto = courseService.getCourseById(courseId);
        // 1. representation from dtos? embedded? ex. course includes a lot of modules, modules contains lessons, lessons contains a lot of questions etc..
        // 2. service must returns responseentity or dto?
        return ResponseEntity.ok(courseDto);
    }

    @Operation(summary = "Create Course", description = "Create Course")
    @PostMapping
    @ValidateErrors
    public ResponseEntity<CourseDto> createNewCourse(
            @RequestBody @Valid CourseDto courseDto,
            BindingResult result,
            @Parameter(hidden = true) @RequestHeader("X-User-Id") Long userId
    ) {
        CourseDto savedCourseDto = courseService.createNewCourse(courseDto, userId);
        return new ResponseEntity<>(savedCourseDto, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete Course by ID", description = "Delete Course by ID")
    @DeleteMapping("/{courseId}")
    public ResponseEntity<ResponseMessage> deleteCourseById(
            @PathVariable Long courseId,
            @Parameter(hidden = true) @RequestHeader("X-User-Id") Long userId
    ) {
        courseService.deleteCourseById(courseId, userId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update Course by ID", description = "Update Course by ID")
    @PutMapping("/{courseId}")
    @ValidateErrors
    public ResponseEntity<CourseDto> updateCourseById(
            @PathVariable Long courseId,
            @Parameter(hidden = true) @RequestHeader("X-User-Id") Long userId,
            @RequestBody @Valid UpdateCourseDto updateCourseDto,
            BindingResult result
    ) {
        CourseDto courseDto = courseService.updateCourseById(courseId, userId, updateCourseDto);
        return ResponseEntity.ok(courseDto);
    }

    @Operation(summary = "Partial Update Course by ID", description = "Partial Update Course by ID")
    @PatchMapping("/{courseId}")
    public ResponseEntity<CourseDto> patchCourseById(
            @PathVariable Long courseId,
            @Parameter(hidden = true) @RequestHeader("X-User-Id") Long userId,
            @RequestBody UpdateCourseDto updateCourseDto,
            BindingResult result
    ) {
        CourseDto courseDto = courseService.patchCourseById(courseId, userId, updateCourseDto);
        return ResponseEntity.ok(courseDto);
    }

}
