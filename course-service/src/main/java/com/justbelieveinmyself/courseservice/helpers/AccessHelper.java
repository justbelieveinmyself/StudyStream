package com.justbelieveinmyself.courseservice.helpers;

import com.justbelieveinmyself.courseservice.services.CourseService;
import com.justbelieveinmyself.library.exception.ForbiddenException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccessHelper {
    private final CourseService courseService;

    public void checkUserHasAccessToCourse(Long courseId, Long userId) {
        if (!courseService.existByIdAndAuthorId(courseId, userId)) {
            throw new ForbiddenException("Only the author can edit his course!");
        }
    }

}
