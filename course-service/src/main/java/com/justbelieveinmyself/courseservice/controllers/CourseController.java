package com.justbelieveinmyself.courseservice.controllers;

import com.justbelieveinmyself.courseservice.domains.dtos.CourseDto;
import com.justbelieveinmyself.courseservice.domains.dtos.UpdateCourseDto;
import com.justbelieveinmyself.courseservice.services.CourseService;
import com.justbelieveinmyself.library.dto.ResponseMessage;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/course")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDto> getCourseById(@PathVariable Long courseId) {
        CourseDto courseDto = courseService.getCourseById(courseId);
        // 1. representation from dtos? embedded? ex. course includes a lot of modules, modules contains lessons, lessons contains a lot of questions etc..
        // 2. service must returns responseentity or dto?
        return ResponseEntity.ok(courseDto);
    }

    @PostMapping
    public ResponseEntity<CourseDto> createNewCourse(@RequestBody @Valid CourseDto courseDto) {
        CourseDto savedCourseDto = courseService.createNewCourse(courseDto);
        return ResponseEntity.ok(savedCourseDto);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<ResponseMessage> deleteCourseById(@PathVariable Long courseId, @RequestHeader("X-UserId") Long userId) {
        courseService.deleteCourseById(courseId, userId);
        return ResponseEntity.ok(new ResponseMessage(200, "Course successfully deleted with id: " + courseId));
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<CourseDto> updateCourseById(@PathVariable Long courseId, @RequestHeader("X-UserId") Long userId, @RequestBody @Valid UpdateCourseDto updateCourseDto) {
        CourseDto courseDto = courseService.updateCourseById(courseId, userId, updateCourseDto);
        return ResponseEntity.ok(courseDto);
    }

}
