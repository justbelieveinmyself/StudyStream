package com.justbelieveinmyself.courseservice.controllers;

import com.justbelieveinmyself.courseservice.domains.dtos.CourseDto;
import com.justbelieveinmyself.courseservice.domains.dtos.update.UpdateCourseDto;
import com.justbelieveinmyself.courseservice.domains.enums.CourseDifficulty;
import com.justbelieveinmyself.courseservice.services.CourseService;
import com.justbelieveinmyself.library.dto.ResponseMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/courses")
@CrossOrigin
@RequiredArgsConstructor
@Log4j2
public class CourseControllerImpl implements CourseController {
    private final CourseService courseService;

    @Override
    public ResponseEntity<Page<CourseDto>> getCourses(String title, CourseDifficulty difficulty, Double price, int pageNumber, int pageSize) {
        Page<CourseDto> courses = courseService.getCourses(title, difficulty, price, pageNumber, pageSize);
        log.info("getCourses returned {}", courses);
        return ResponseEntity.ok(courses);
    }

    @Override
    public ResponseEntity<CourseDto> getCourseById(Long courseId) {
        CourseDto courseDto = courseService.getCourseById(courseId);
        // 1. representation from dtos? embedded? ex. course includes a lot of modules, modules contains lessons, lessons contains a lot of questions etc..
        // 2. service must returns responseentity or dto?
        return ResponseEntity.ok(courseDto);
    }

    @Override
    public ResponseEntity<CourseDto> createNewCourse(CourseDto courseDto, BindingResult result, Long userId) {
        CourseDto savedCourseDto = courseService.createNewCourse(courseDto, userId);
        return new ResponseEntity<>(savedCourseDto, HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ResponseMessage> deleteCourseById(Long courseId, Long userId) {
        courseService.deleteCourseById(courseId, userId);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<CourseDto> updateCourseById(
            Long courseId, Long userId,
            UpdateCourseDto updateCourseDto,
            BindingResult result
    ) {
        CourseDto courseDto = courseService.updateCourseById(courseId, userId, updateCourseDto);
        return ResponseEntity.ok(courseDto);
    }

    @Override
    public ResponseEntity<CourseDto> patchCourseById(
            Long courseId, Long userId,
            UpdateCourseDto updateCourseDto,
            BindingResult result
    ) {
        CourseDto courseDto = courseService.patchCourseById(courseId, userId, updateCourseDto);
        return ResponseEntity.ok(courseDto);
    }

}
