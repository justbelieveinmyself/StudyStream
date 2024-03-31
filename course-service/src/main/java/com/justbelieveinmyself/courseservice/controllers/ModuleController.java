package com.justbelieveinmyself.courseservice.controllers;

import com.justbelieveinmyself.courseservice.domains.annotations.CheckModuleExistsInCourse;
import com.justbelieveinmyself.courseservice.domains.dtos.ModuleDto;
import com.justbelieveinmyself.courseservice.domains.dtos.UpdateModuleDto;
import com.justbelieveinmyself.courseservice.services.ModuleService;
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
@RequestMapping("/api/v1/course/{courseId}/module")
@CrossOrigin
@RequiredArgsConstructor
@Tag(name = "Module API", description = "Create, get, update and delete modules")
@SecurityRequirement(name = "Bearer Authentication")
public class ModuleController {
    private final ModuleService moduleService;

    @Operation(summary = "Get Module by ID", description = "Get Module by ID")
    @GetMapping("/{moduleId}")
    @CheckModuleExistsInCourse
    public ResponseEntity<ModuleDto> getModuleById(
            @PathVariable Long courseId,
            @PathVariable Long moduleId
    ) {
        ModuleDto moduleDto = moduleService.getModuleById(moduleId);
        return ResponseEntity.ok(moduleDto);
    }

    @Operation(summary = "Create Module", description = "Create Module")
    @PostMapping
    @ValidateErrors
    public ResponseEntity<ModuleDto> createNewModule(
            @RequestBody @Valid ModuleDto moduleDto,
            BindingResult result,
            @PathVariable Long courseId,
            @Parameter(hidden = true) @RequestHeader("X-User-Id") Long userId
    ) {
        ModuleDto savedModuleDto = moduleService.createNewModule(moduleDto, courseId, userId);
        return new ResponseEntity<>(savedModuleDto, HttpStatus.CREATED);
    }

    @Operation(summary = "Delete Module by ID", description = "Delete Module by ID")
    @DeleteMapping("/{moduleId}")
    @CheckModuleExistsInCourse
    public ResponseEntity<ResponseMessage> deleteModuleById(
            @PathVariable Long courseId,
            @PathVariable Long moduleId,
            @Parameter(hidden = true) @RequestHeader("X-User-Id") Long userId
    ) {
        moduleService.deleteModuleById(courseId, moduleId, userId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Update Module by ID", description = "Update Module by ID")
    @PutMapping("/{moduleId}")
    @ValidateErrors
    @CheckModuleExistsInCourse
    public ResponseEntity<ModuleDto> updateModuleById(
            @PathVariable Long courseId,
            @PathVariable Long moduleId,
            @Parameter(hidden = true) @RequestHeader("X-User-Id") Long userId,
            @RequestBody @Valid UpdateModuleDto updateModuleDto,
            BindingResult result
    ) {
        ModuleDto moduleDto = moduleService.updateModuleById(courseId, moduleId, userId, updateModuleDto);
        return ResponseEntity.ok(moduleDto);
    }

}
