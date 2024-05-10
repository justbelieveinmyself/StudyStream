package com.justbelieveinmyself.courseservice.controllers;

import com.justbelieveinmyself.courseservice.domains.annotations.CheckLessonExistsInModule;
import com.justbelieveinmyself.courseservice.domains.annotations.CheckModuleExistsInCourse;
import com.justbelieveinmyself.courseservice.domains.dtos.LessonDto;
import com.justbelieveinmyself.courseservice.services.LessonService;
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
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/courses/{courseId}/modules/{moduleId}/lessons")
@CrossOrigin
@RequiredArgsConstructor
@Tag(name = "Lesson API", description = "Create, get, update and delete lesson")
@SecurityRequirement(name = "Bearer Authentication")
public class LessonController { //TODO: controllers to interfaces?
    private final LessonService lessonService;

    @Operation(summary = "Get Lesson by ID", description = "Get Lesson by ID")
    @GetMapping("/{lessonId}")
    @CheckLessonExistsInModule
    public ResponseEntity<LessonDto> getLessonById(
            @PathVariable Long courseId,
            @PathVariable Long moduleId,
            @PathVariable Long lessonId
    ) {
        LessonDto lessonDto = lessonService.getLessonById(lessonId);
        return ResponseEntity.ok(lessonDto);
    }

    @Operation(summary = "Create Lesson", description = "Create Lesson")
    @PostMapping
    @ValidateErrors
    @CheckModuleExistsInCourse
    public ResponseEntity<LessonDto> createNewLesson(
            @PathVariable Long courseId,
            @PathVariable Long moduleId,
            @RequestBody @Valid LessonDto lessonDto,
            BindingResult result,
            @Parameter(hidden = true) @RequestHeader("X-User-Id") Long userId
    ) {
        LessonDto savedLessonDto = lessonService.createNewLesson(lessonDto, courseId, moduleId, userId);
        return new ResponseEntity<>(savedLessonDto, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete Lesson by ID", description = "Delete Lesson by ID")
    @DeleteMapping("{lessonId}")
    @CheckLessonExistsInModule
    public ResponseEntity<ResponseMessage> deleteLessonById(
            @PathVariable Long courseId,
            @PathVariable Long moduleId,
            @PathVariable Long lessonId,
            @Parameter(hidden = true) @RequestHeader("X-User-Id") Long userId
    ) {
        lessonService.deleteLessonById(courseId, lessonId, userId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update Lesson by ID", description = "Update Lesson by ID")
    @PutMapping("/{lessonId}")
    @ValidateErrors
    @CheckLessonExistsInModule
    public ResponseEntity<LessonDto> updateLessonById(
            @PathVariable Long courseId,
            @PathVariable Long moduleId,
            @PathVariable Long lessonId,
            @Parameter(hidden = true) @RequestHeader("X-User-Id") Long userId,
            @RequestBody @Valid LessonDto lessonDto,
            BindingResult result
    ) {
        LessonDto lesson = lessonService.updateLessonById(courseId, lessonId, userId, lessonDto);
        return ResponseEntity.ok(lesson);
    }

    @Operation(summary = "Partial Update Lesson by ID", description = "Partial Update Lesson by ID")
    @PatchMapping("/{lessonId}")
    @CheckLessonExistsInModule
    public ResponseEntity<LessonDto> patchLessonById(
            @PathVariable Long courseId,
            @PathVariable Long moduleId,
            @PathVariable Long lessonId,
            @Parameter(hidden = true) @RequestHeader("X-User-Id") Long userId,
            @RequestBody LessonDto lessonDto,
            BindingResult result
    ) {
        LessonDto lesson = lessonService.patchLessonById(courseId, lessonId, userId, lessonDto);
        return ResponseEntity.ok(lesson);
    }

    @Operation(summary = "Add resources to lesson.")
    @PostMapping(value = "{lessonId}/resources", consumes = "application/octet-stream")
    @CheckLessonExistsInModule
    public ResponseEntity<ResponseMessage> addResources(
            @PathVariable Long courseId,
            @PathVariable Long moduleId,
            @PathVariable Long lessonId,
            @Parameter(hidden = true) @RequestHeader("X-User-Id") Long userId,
            @RequestBody MultipartFile[] files
    ){
        lessonService.addResources(courseId, lessonId, userId, files);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}