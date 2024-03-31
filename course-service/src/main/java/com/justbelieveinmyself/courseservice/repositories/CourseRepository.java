package com.justbelieveinmyself.courseservice.repositories;

import com.justbelieveinmyself.courseservice.domains.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    boolean existsByIdAndAuthorId(Long courseId, Long authorId);
    boolean existsByIdAndModulesId(Long courseId, Long moduleId);
}
