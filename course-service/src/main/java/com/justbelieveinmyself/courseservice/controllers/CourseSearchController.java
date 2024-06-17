package com.justbelieveinmyself.courseservice.controllers;

import com.justbelieveinmyself.courseservice.domains.entities.CourseES;
import com.justbelieveinmyself.courseservice.services.CourseSearchService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/courses/search")
@CrossOrigin
@SecurityRequirement(name = "Bearer Authentication")
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
