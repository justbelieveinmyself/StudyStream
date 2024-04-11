package com.justbelieveinmyself.courseservice.services;

import com.justbelieveinmyself.courseservice.domains.dtos.CourseDto;
import com.justbelieveinmyself.courseservice.domains.dtos.ModuleDto;
import com.justbelieveinmyself.courseservice.domains.dtos.update.UpdateCourseDto;
import com.justbelieveinmyself.courseservice.domains.entities.Course;
import com.justbelieveinmyself.courseservice.domains.entities.Module;
import com.justbelieveinmyself.courseservice.domains.entities.User;
import com.justbelieveinmyself.courseservice.domains.enums.CourseDifficulty;
import com.justbelieveinmyself.courseservice.helpers.AccessHelper;
import com.justbelieveinmyself.courseservice.repositories.CourseRepository;
import com.justbelieveinmyself.courseservice.repositories.specifications.CourseSpecifications;
import com.justbelieveinmyself.library.dto.ModelUtils;
import com.justbelieveinmyself.library.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

    public Page<CourseDto> getCourses(String title, CourseDifficulty difficulty, Double price, int pageNumber, int pageSize) {
        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Specification<Course> specification = Specification.where(null);
        if (title != null) {
            specification = Specification
                    .where(CourseSpecifications.hasTitle(title));
        }
        if (difficulty != null) {
            specification = specification.and(CourseSpecifications.withDifficulty(difficulty));
        }
        if (price != null) {
            specification = specification.and(CourseSpecifications.cheaperThan(price));
        }

        Page<Course> pageOfCourse = courseRepository.findAll(specification, pageable);

        List<CourseDto> courseDtos = pageOfCourse.getContent()
                .stream()
                .map(course -> new CourseDto().fromEntity(course))
                .collect(Collectors.toList());
        return new PageImpl<>(courseDtos, pageable, pageOfCourse.getTotalElements());
    }

    @Transactional
    public CourseDto createNewCourse(CourseDto courseDto, Long authorId) {
        User author = userService.findById(authorId);
        Course course = courseDto.toEntity();
        course.setAuthor(author);
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

        ModelUtils.copyPropertiesIgnoreNull(updateCourseDto, course);
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
