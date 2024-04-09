package com.justbelieveinmyself.courseservice.repositories.specifications;

import com.justbelieveinmyself.courseservice.domains.entities.Course;
import com.justbelieveinmyself.courseservice.domains.enums.CourseDifficulty;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;

public class CourseSpecifications {

    public static Specification<Course> hasTitle(String title) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("title"), "%" + title + "%");
    }

    public static Specification<Course> withDifficulty(CourseDifficulty courseDifficulty) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("difficulty"), courseDifficulty.name());
    }

    public static Specification<Course> cheaperThan(double price) {
        return (root, query, criteriaBuilder) -> criteriaBuilder.lessThan(root.get("price"), price);
    }

}
