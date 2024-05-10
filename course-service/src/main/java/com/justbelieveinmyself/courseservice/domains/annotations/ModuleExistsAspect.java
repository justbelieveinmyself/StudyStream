package com.justbelieveinmyself.courseservice.domains.annotations;

import com.justbelieveinmyself.courseservice.repositories.CourseRepository;
import com.justbelieveinmyself.library.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class ModuleExistsAspect {
    private final CourseRepository courseRepository;

    @Before("@annotation(com.justbelieveinmyself.courseservice.domains.annotations.CheckModuleExistsInCourse)")
    public void checkModuleExistsInCourse(JoinPoint joinPoint) {
        Long courseId = Long.parseLong(joinPoint.getArgs()[0].toString());
        Long moduleId = Long.parseLong(joinPoint.getArgs()[1].toString());

        if (!courseRepository.existsByIdAndModulesId(courseId, moduleId)) {
            throw new NotFoundException("Course with ID: " + courseId + " does not contain module with ID: " + moduleId);
        }
    }

}
