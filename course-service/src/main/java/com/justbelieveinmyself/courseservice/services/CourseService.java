package com.justbelieveinmyself.courseservice.services;

import com.justbelieveinmyself.courseservice.domains.dtos.CourseDto;
import com.justbelieveinmyself.courseservice.domains.dtos.UpdateCourseDto;
import com.justbelieveinmyself.courseservice.domains.entities.Course;
import com.justbelieveinmyself.courseservice.repositories.CourseRepository;
import com.justbelieveinmyself.library.exception.ForbiddenException;
import com.justbelieveinmyself.library.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;


    public CourseDto getCourseById(Long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new NotFoundException("Course not found with CourseID: " + courseId));
        return new CourseDto().fromEntity(course);
    }

    public CourseDto createNewCourse(CourseDto courseDto) {
        Course course = courseDto.toEntity();
        Course savedCourse = courseRepository.save(course);
        return courseDto.fromEntity(savedCourse);
    }

    public void deleteCourseById(Long courseId, Long authorUserId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new NotFoundException("Course not found with CourseID: " + courseId));
        if (!course.getAuthor().getId().equals(authorUserId)) {
            throw new ForbiddenException("Only the author can delete his course!");
        }
        courseRepository.delete(course);
    }

    public CourseDto updateCourseById(Long courseId, Long authorUserId, UpdateCourseDto updateCourseDto) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new NotFoundException("Course not found with CourseID: " + courseId));
        if (!course.getAuthor().getId().equals(authorUserId)) {
            throw new ForbiddenException("Only the author can delete his course!");
        }
        BeanUtils.copyProperties(updateCourseDto, course);

        Course updatedCourse = courseRepository.save(course);
        return new CourseDto().fromEntity(updatedCourse);
    }

}
