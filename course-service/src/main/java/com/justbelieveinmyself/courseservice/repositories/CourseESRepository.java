package com.justbelieveinmyself.courseservice.repositories;

import com.justbelieveinmyself.courseservice.domains.entities.CourseES;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseESRepository extends ElasticsearchRepository<CourseES, Long> {
    List<CourseES> findByTitleOrDescriptionOrCategoryContainingIgnoreCase(String title, String description, String category, Pageable page);
}
