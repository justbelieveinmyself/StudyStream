package com.justbelieveinmyself.courseservice.controllers;

import com.justbelieveinmyself.courseservice.domains.entities.CourseES;
import com.justbelieveinmyself.courseservice.services.CourseSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses/search")
@RequiredArgsConstructor
public class CourseSearchController {
    private final CourseSearchService courseSearchService;

    @GetMapping
    public ResponseEntity<List<CourseES>> getCourses(
            @RequestParam(required = false, defaultValue = "0") int pageNum,
            @RequestParam(required = false, defaultValue = "5") int pageSize,
            @RequestParam(required = false) String keyword
    ) {
        return ResponseEntity.ok(courseSearchService.searchCourses(keyword, pageNum, pageSize));
    }
}
