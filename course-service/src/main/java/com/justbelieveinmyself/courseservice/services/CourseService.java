package com.justbelieveinmyself.courseservice.services;

import com.justbelieveinmyself.courseservice.domains.dtos.CourseDto;
import com.justbelieveinmyself.courseservice.domains.dtos.ModuleDto;
import com.justbelieveinmyself.courseservice.domains.dtos.update.UpdateCourseDto;
import com.justbelieveinmyself.courseservice.domains.entities.Course;
import com.justbelieveinmyself.courseservice.domains.entities.Module;
import com.justbelieveinmyself.courseservice.domains.entities.User;
import com.justbelieveinmyself.courseservice.helpers.AccessHelper;
import com.justbelieveinmyself.courseservice.repositories.CourseRepository;
import com.justbelieveinmyself.library.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final UserService userService;
    private AccessHelper accessHelper;

    public Course findById(Long courseId) {
        return courseRepository.findById(courseId).orElseThrow(() -> new NotFoundException("Course not found with CourseID: " + courseId));
    }

    public boolean existByIdAndAuthorId(Long courseId, Long authorId) {
        return courseRepository.existsByIdAndAuthorId(courseId, authorId);
    }

    public CourseDto getCourseById(Long courseId) {
        Course course = findById(courseId);
        return new CourseDto().fromEntity(course);
    }

    @Transactional
    public CourseDto createNewCourse(CourseDto courseDto, Long authorId) {
        User author = userService.findById(authorId);
        Course course = courseDto.toEntity();
        course.setAuthor(author); //TODO: CHECK CREATION TIME ON ENTITIES
        Course savedCourse = courseRepository.saveAndFlush(course);
        return courseDto.fromEntity(savedCourse);
    }

    public void deleteCourseById(Long courseId, Long authorUserId) {
        accessHelper.checkUserHasAccessToCourse(courseId, authorUserId);

        courseRepository.deleteById(courseId);
    }

    public CourseDto updateCourseById(Long courseId, Long authorUserId, UpdateCourseDto updateCourseDto) {
        Course course = findById(courseId);

        accessHelper.checkUserHasAccessToCourse(courseId, authorUserId);

        BeanUtils.copyProperties(updateCourseDto, course);
        setModulesInCourseFromUpdateCourseDto(updateCourseDto, course);

        Course savedCourse = courseRepository.save(course);
        return new CourseDto().fromEntity(savedCourse);
    }

    public CourseDto patchCourseById(Long courseId, Long authorUserId, UpdateCourseDto updateCourseDto) {
        Course course = findById(courseId);

        accessHelper.checkUserHasAccessToCourse(courseId, authorUserId);

        if (updateCourseDto.getTitle() != null) {
            course.setTitle(updateCourseDto.getTitle());
        }
        if (updateCourseDto.getCategory() != null) {
            course.setCategory(updateCourseDto.getCategory());
        }
        if (updateCourseDto.getDescription() != null) {
            course.setDescription(updateCourseDto.getDescription());
        }
        if (updateCourseDto.getDuration() != null) {
            course.setDuration(updateCourseDto.getDuration());
        }
        if (updateCourseDto.getPrice() != null) {
            course.setPrice(updateCourseDto.getPrice());
        }
        if (updateCourseDto.getStatus() != null) {
            course.setStatus(updateCourseDto.getStatus());
        }
        if (updateCourseDto.getDifficulty() != null) {
            course.setDifficulty(updateCourseDto.getDifficulty());
        }
        setModulesInCourseFromUpdateCourseDto(updateCourseDto, course);

        Course savedCourse = courseRepository.save(course);
        return new CourseDto().fromEntity(savedCourse);
    }

    private void setModulesInCourseFromUpdateCourseDto(UpdateCourseDto updateCourseDto, Course course) {
        if (updateCourseDto.getModules() != null) {

            List<Module> modules = new ArrayList<>();

            for (ModuleDto moduleDto : updateCourseDto.getModules()) {
                Module module = moduleDto.toEntity();
                module.setCourse(course);
                modules.add(module);
            }

            course.setModules(modules);
        }
    }

}
