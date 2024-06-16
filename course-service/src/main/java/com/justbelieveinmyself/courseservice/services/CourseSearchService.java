package com.justbelieveinmyself.courseservice.services;

import com.justbelieveinmyself.courseservice.domains.entities.CourseES;
import com.justbelieveinmyself.courseservice.repositories.CourseESRepository;
import lombok.RequiredArgsConstructor;
import org.jvnet.hk2.annotations.Service;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseSearchService {
    private final CourseESRepository courseESRepository;

    public List<CourseES> searchCourses(String keyword, int pageNum, int pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return courseESRepository.findByTitleOrDescriptionOrKeywordsContainingIgnoreCase(keyword, keyword, keyword, pageable);
    }

}
