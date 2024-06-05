package com.justbelieveinmyself.courseservice.services;

import com.justbelieveinmyself.courseservice.domains.dtos.CourseDto;
import com.justbelieveinmyself.courseservice.domains.entities.Course;
import com.justbelieveinmyself.courseservice.domains.enums.CourseDifficulty;
import com.justbelieveinmyself.courseservice.helpers.AccessHelper;
import com.justbelieveinmyself.courseservice.repositories.CourseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CourseServiceTest {
    @InjectMocks
    private CourseService courseService;
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private UserService userService;
    @MockBean
    private AccessHelper accessHelper;

    @Test
    void findById() {
        final long courseId = 1L;

        when(courseRepository.findById(courseId))
                .thenReturn(Optional.of(Course.builder().id(courseId).build()));

        Course course = courseService.findById(courseId);

        assertEquals(course.getId(), courseId);
        verify(courseRepository, times(1)).findById(courseId);
    }

    @Test
    void existByIdAndAuthorId() {
        final long courseId = 1L;
        final long authorId = 1L;

        when(courseRepository.existsByIdAndAuthorId(courseId, authorId))
                .thenReturn(true);


        boolean exists = courseService.existByIdAndAuthorId(courseId, authorId);


        assertTrue(exists);
        verify(courseRepository, times(1)).existsByIdAndAuthorId(courseId, authorId);
    }

    @Test
    void getCourseById() {
        final long courseId = 1L;

        when(courseRepository.findById(courseId))
                .thenReturn(Optional.of(Course.builder().id(courseId).build()));

        CourseDto course = courseService.getCourseById(courseId);

        verify(courseRepository, times(1)).findById(courseId);
    }

    @Test
    void getCourses() {
        final String title = "Title";
        final CourseDifficulty courseDifficulty = CourseDifficulty.BEGINNER;
        final double price = 100;
        final int pageNumber = 1;
        final int pageSize = 10;


        Page<CourseDto> courses = courseService.getCourses(title, courseDifficulty, price, pageNumber, pageSize);

        verify(courseRepository, times(1)).findAll(any(), any());
    }

    @Test
    void createNewCourse() {
    }

    @Test
    void deleteCourseById() {
    }

    @Test
    void updateCourseById() {
    }

    @Test
    void patchCourseById() {
    }
}