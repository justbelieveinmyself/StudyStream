package com.justbelieveinmyself.courseservice.controllers;

import com.justbelieveinmyself.courseservice.domains.annotations.CheckModuleExistsInCourse;
import com.justbelieveinmyself.courseservice.domains.dtos.LessonDto;
import com.justbelieveinmyself.courseservice.domains.dtos.ModuleDto;
import com.justbelieveinmyself.courseservice.domains.dtos.UpdateLessonDto;
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

@RestController
@RequestMapping("/api/v1/course/{courseId}/module/{moduleId}/lesson")
@CrossOrigin
@RequiredArgsConstructor
@Tag(name = "Lesson API", description = "Create, get, update and delete lesson")
@SecurityRequirement(name = "Bearer Authentication")
public class LessonController { //TODO: controllers to interfaces?
    private final LessonService lessonService;

    @Operation(summary = "Get Lesson by ID", description = "Get Lesson by ID")
    @GetMapping("/{lessonId}")
    @CheckModuleExistsInCourse//TODO: check what lesson exists in module?
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
            @RequestBody @Valid LessonDto lessonDto,
            BindingResult result,
            @PathVariable Long courseId,
            @PathVariable Long moduleId,
            @Parameter(hidden = true) @RequestHeader("X-User-Id") Long userId
    ) {
        LessonDto savedLessonDto = lessonService.createNewLesson(lessonDto, courseId, userId);
        return new ResponseEntity<>(savedLessonDto, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete Lesson by ID", description = "Delete Lesson by ID")
    @DeleteMapping("{lessonId}")
    @CheckModuleExistsInCourse
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
    @CheckModuleExistsInCourse
    public ResponseEntity<LessonDto> updateLessonById(
            @PathVariable Long courseId,
            @PathVariable Long moduleId,
            @PathVariable Long lessonId,
            @Parameter(hidden = true) @RequestHeader("X-User-Id") Long userId,
            @RequestBody @Valid UpdateLessonDto updateLessonDto,
            BindingResult result
    ) {
        LessonDto lessonDto = lessonService.updateLessonById(courseId, moduleId, userId, updateLessonDto);
        return ResponseEntity.ok(lessonDto);
    }

    @Operation(summary = "Partial Update Lesson by ID", description = "Partial Update Lesson by ID")
    @PatchMapping("/{lessonId}")
    @CheckModuleExistsInCourse
    public ResponseEntity<ModuleDto> patchLessonById(
            @PathVariable Long courseId,
            @PathVariable Long moduleId,
            @PathVariable Long lessonId,
            @Parameter(hidden = true) @RequestHeader("X-User-Id") Long userId,
            @RequestBody UpdateLessonDto updateLessonDto,
            BindingResult result
    ) {
        ModuleDto moduleDto = lessonService.patchLessonById(courseId, moduleId, userId, updateLessonDto);
        return ResponseEntity.ok(moduleDto);
    }

}