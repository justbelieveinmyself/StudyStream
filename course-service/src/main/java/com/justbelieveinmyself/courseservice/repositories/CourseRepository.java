package com.justbelieveinmyself.courseservice.repositories;

import com.justbelieveinmyself.courseservice.domains.entities.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>, JpaSpecificationExecutor<Course> {
    boolean existsByIdAndAuthorId(Long courseId, Long authorId);
    boolean existsByIdAndModulesId(Long courseId, Long moduleId);
    boolean existsByIdAndModulesIdAndModulesLessonsId(Long courseId, Long moduleId, Long lessonId);
}
