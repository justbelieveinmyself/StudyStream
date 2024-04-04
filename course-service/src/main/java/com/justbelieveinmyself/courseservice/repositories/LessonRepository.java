package com.justbelieveinmyself.courseservice.repositories;

import com.justbelieveinmyself.courseservice.domains.entities.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {
}
