package com.justbelieveinmyself.courseservice.helpers;

import com.justbelieveinmyself.courseservice.repositories.CourseRepository;
import com.justbelieveinmyself.library.exception.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccessHelper {
    private final CourseRepository courseRepository;

    public void checkUserHasAccessToCourse(Long courseId, Long userId) {
        if (!courseRepository.existsByIdAndAuthorId(courseId, userId)) {
            throw new ForbiddenException("Only the author can edit his course!");
        }
    }

}
