package com.justbelieveinmyself.courseservice.services;

import com.justbelieveinmyself.courseservice.domains.dtos.CourseDto;
import com.justbelieveinmyself.courseservice.domains.dtos.ModuleDto;
import com.justbelieveinmyself.courseservice.domains.dtos.update.UpdateCourseDto;
import com.justbelieveinmyself.courseservice.domains.entities.Course;
import com.justbelieveinmyself.courseservice.domains.entities.Module;
import com.justbelieveinmyself.courseservice.domains.entities.User;
import com.justbelieveinmyself.courseservice.domains.enums.CourseDifficulty;
import com.justbelieveinmyself.courseservice.domains.enums.CourseStatus;
import com.justbelieveinmyself.courseservice.helpers.AccessHelper;
import com.justbelieveinmyself.courseservice.repositories.CourseRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.*;

import static com.justbelieveinmyself.courseservice.domains.enums.CourseDifficulty.ADVANCED;
import static com.justbelieveinmyself.courseservice.domains.enums.CourseDifficulty.BEGINNER;
import static com.justbelieveinmyself.courseservice.domains.enums.CourseStatus.ACTIVE;
import static java.math.BigDecimal.valueOf;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class CourseServiceTest {
    @InjectMocks
    private CourseService courseService;
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private UserService userService;
    @Mock
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
        final long authorId = 1L;

        when(courseRepository.findById(courseId))
                .thenReturn(Optional.of(
                        Course.builder()
                                .id(courseId)
                                .author(User.builder()
                                        .id(authorId)
                                        .build()).build()));

        CourseDto course = courseService.getCourseById(courseId);

        verify(courseRepository, times(1)).findById(courseId);
        assertEquals(course.getAuthorId(), authorId);
    }

    @Test
    void getCourses() {
        final String title = "Title";
        final CourseDifficulty courseDifficulty = BEGINNER;
        final double price = 100;
        final int pageNumber = 1;
        final int pageSize = 10;
        final int totalElements = 12;
        final long firstCourseId = 1L;
        final long secondCourseId = 2L;
        final BigDecimal firstCoursePrice = valueOf(50);
        final BigDecimal secondCoursePrice = valueOf(70);
        final String firstCourseTitle = title + "any text";
        final String secondCourseTitle = title + "popda";
        final long authorId = 1L;
        final User author = User.builder().id(authorId).build();
        List<Course> courses = new ArrayList<>(Arrays.asList(
                Course.builder()
                        .id(firstCourseId)
                        .author(author)
                        .difficulty(courseDifficulty)
                        .price(firstCoursePrice)
                        .title(firstCourseTitle).build(),
                Course.builder()
                        .id(secondCourseId)
                        .author(author)
                        .difficulty(courseDifficulty)
                        .price(secondCoursePrice)
                        .title(secondCourseTitle).build()
        ));
        author.setCreatedCourses(new HashSet<>(courses));

        when(courseRepository.findAll(any(Specification.class), any(Pageable.class)))
                .thenAnswer(i -> new PageImpl<>(courses, i.getArgument(1), totalElements));

        Page<CourseDto> pageOfCourses = courseService.getCourses(title, courseDifficulty, price, pageNumber, pageSize);


        verify(courseRepository, times(1)).findAll(any(Specification.class), any(Pageable.class));
        assertEquals(pageOfCourses.getTotalElements(), totalElements);
        assertEquals(pageOfCourses.getTotalPages(), Math.ceil((double) totalElements / pageSize));
        assertEquals(pageOfCourses.getNumber(), pageNumber);
        assertEquals(pageOfCourses.getSize(), pageSize);
        assertEquals(pageOfCourses.getNumberOfElements(), courses.size());

        List<CourseDto> content = pageOfCourses.getContent();
        assertEquals(content.get(0).getTitle(), firstCourseTitle);
        assertEquals(content.get(1).getTitle(), secondCourseTitle);
        assertEquals(content.get(0).getId(), firstCourseId);
        assertEquals(content.get(1).getId(), secondCourseId);
        assertEquals(content.get(0).getAuthorId(), authorId);
        assertEquals(content.get(1).getAuthorId(), authorId);
        assertEquals(content.get(0).getDifficulty(), courseDifficulty);
        assertEquals(content.get(1).getDifficulty(), courseDifficulty);
        assertEquals(content.get(0).getPrice(), firstCoursePrice);
        assertEquals(content.get(1).getPrice(), secondCoursePrice);

    }

    @Test
    void createNewCourse() {
        final long courseId = 1L;
        final long authorId = 1L;
        final BigDecimal price = valueOf(100);
        String category = "Category";
        final String courseTitle = "Course Title";
        final long moduleId = 1L;
        String moduleTitle = "Module Title";
        String moduleDescription = "Module Description";

        List<ModuleDto> moduleDtos = new ArrayList<>(Arrays.asList(
                ModuleDto.builder()
                        .id(moduleId)
                        .courseId(courseId)
                        .title(moduleTitle)
                        .creationTime(Instant.ofEpochMilli(3333))
                        .description(moduleDescription)
                        .build()
        ));

        String courseDescription = "Course Description";
        int duration = 10;
        double rating = 10d;
        CourseDifficulty difficulty = BEGINNER;
        CourseStatus status = ACTIVE;

        CourseDto requestDto = CourseDto.builder()
                .id(courseId)
                .title(courseTitle)
                .difficulty(difficulty)
                .authorId(authorId)
                .description(courseDescription)
                .duration(duration)
                .rating(rating)
                .creationTime(Instant.ofEpochMilli(30000))
                .category(category)
                .status(status)
                .modules(moduleDtos)
                .price(price)
                .build();

        when(userService.findById(authorId))
                .thenReturn(User.builder().id(authorId).build());
        when(courseRepository.saveAndFlush(any(Course.class))).thenAnswer(i -> i.getArgument(0));

        CourseDto responseDto = courseService.createNewCourse(requestDto, authorId);
        verify(courseRepository, times(1)).saveAndFlush(any(Course.class));
        verify(userService, times(1)).findById(authorId);
        assertEquals(responseDto.getAuthorId(), authorId);
        assertNull(responseDto.getId());
        assertNull(responseDto.getRating());
        assertNull(responseDto.getCreationTime());
        assertEquals(responseDto.getTitle(), courseTitle);
        assertEquals(responseDto.getDescription(), courseDescription);
        assertEquals(responseDto.getStatus(), status);
        assertEquals(responseDto.getDifficulty(), difficulty);
        assertEquals(responseDto.getDuration(), duration);
        assertEquals(responseDto.getModules().size(), 1);
        ModuleDto responseModuleDto = responseDto.getModules().get(0);
        assertNull(responseModuleDto.getId());
        assertNull(responseModuleDto.getCourseId());
        assertNull(responseModuleDto.getCreationTime());
        assertEquals(responseModuleDto.getLessons().size(), 0);
        assertEquals(responseModuleDto.getTitle(), moduleTitle);
        assertEquals(responseModuleDto.getDescription(), moduleDescription);
    }

    @Test
    void deleteCourseById() {
        final long courseId = 1L;
        final long authorId = 2L;

        doNothing().when(accessHelper).checkUserHasAccessToCourse(courseId, authorId);

        courseService.deleteCourseById(courseId, authorId);

        verify(courseRepository, times(1)).deleteById(courseId);
        verify(accessHelper, times(1)).checkUserHasAccessToCourse(courseId, authorId);
    }

    @Test
    void updateCourseById() {
        final long courseId = 1L;
        final long authorId = 2L;
        final long moduleId = 2L;
        final BigDecimal price = valueOf(200);
        final String category = "Category";
        final String description = "Description";
        final CourseDifficulty difficulty = BEGINNER;
        final int duration = 20;
        final CourseStatus status = ACTIVE;
        final String moduleTitle = "Module Title";
        final String moduleDescription = "Module Description";

        List<ModuleDto> moduleDtos = new ArrayList<>(Arrays.asList(
                ModuleDto.builder()
                        .id(moduleId)
                        .courseId(courseId)
                        .title(moduleTitle)
                        .creationTime(Instant.ofEpochMilli(3333))
                        .description(moduleDescription).build()
        ));


        UpdateCourseDto updateCourseDto = UpdateCourseDto.builder()
                .price(price)
                .category(category)
                .description(description)
                .difficulty(difficulty)
                .duration(duration)
                .modules(moduleDtos)
                .status(status).build();

        User author = User.builder().id(authorId).build();

        final String oldModuleTitle = "Old Module Title";
        final long oldModuleId = 2L;

        Course oldCourse = Course.builder()
                .id(courseId)
                .title("old")
                .author(author)
                .difficulty(ADVANCED)
                .price(valueOf(1)).build();

        List<Module> oldModules = Arrays.asList(
                Module.builder()
                        .id(oldModuleId)
                        .title(oldModuleTitle)
                        .course(oldCourse).build()
        );

        oldCourse.setModules(oldModules);

        author.setCreatedCourses(new HashSet<>(Arrays.asList(oldCourse)));

        when(courseRepository.findById(courseId)).thenReturn(Optional.ofNullable(oldCourse));
        when(courseRepository.save(any(Course.class))).thenAnswer(i -> i.getArgument(0));


        CourseDto updatedCourseDto = courseService.updateCourseById(courseId, authorId, updateCourseDto);


        verify(courseRepository, times(1)).findById(courseId);
        verify(accessHelper, times(1)).checkUserHasAccessToCourse(courseId, authorId);
        assertNull(updatedCourseDto.getTitle());
        assertEquals(updatedCourseDto.getPrice(), price);
        assertEquals(updatedCourseDto.getCategory(), category);
        assertEquals(updatedCourseDto.getDescription(), description);
        assertEquals(updatedCourseDto.getStatus(), status);
        assertEquals(updatedCourseDto.getDifficulty(), difficulty);
        assertEquals(updatedCourseDto.getDuration(), duration);
        assertEquals(updatedCourseDto.getModules().size(), 1);
        ModuleDto responseModuleDto = updatedCourseDto.getModules().get(0);
        assertNull(responseModuleDto.getId());
        assertEquals(responseModuleDto.getCourseId(), courseId);
        assertNull(responseModuleDto.getCreationTime());
        assertEquals(responseModuleDto.getTitle(), moduleTitle);
        assertEquals(responseModuleDto.getDescription(), moduleDescription);

    }

    @Test
    void patchCourseById() {
        final long courseId = 1L;
        final long authorId = 2L;
        final long moduleId = 2L;
        final BigDecimal price = valueOf(200);
        final String category = "Category";
        final String description = "Description";
        final CourseDifficulty difficulty = BEGINNER;
        final int duration = 20;
        final CourseStatus status = ACTIVE;
        final String moduleTitle = "Module Title";
        final String moduleDescription = "Module Description";

        List<ModuleDto> moduleDtos = new ArrayList<>(Arrays.asList(
                ModuleDto.builder()
                        .id(moduleId)
                        .courseId(courseId)
                        .title(moduleTitle)
                        .creationTime(Instant.ofEpochMilli(3333))
                        .description(moduleDescription)
                        .build()
        ));


        UpdateCourseDto updateCourseDto = UpdateCourseDto.builder()
                .price(price)
                .category(category)
                .description(description)
                .difficulty(difficulty)
                .duration(duration)
                .modules(moduleDtos)
                .status(status)
                .build();

        User author = User.builder().id(authorId).build();

        final String oldModuleTitle = "Old Module Title";
        final long oldModuleId = 2L;

        Course oldCourse = Course.builder()
                .id(courseId)
                .title("old")
                .author(author)
                .difficulty(ADVANCED)
                .price(valueOf(1)).build();

        List<Module> oldModules = Arrays.asList(
                Module.builder()
                        .id(oldModuleId)
                        .title(oldModuleTitle)
                        .course(oldCourse)
                        .build()
        );

        oldCourse.setModules(oldModules);
        author.setCreatedCourses(new HashSet<>(Arrays.asList(oldCourse)));

        when(courseRepository.findById(courseId)).thenReturn(Optional.ofNullable(oldCourse));
        when(courseRepository.save(any(Course.class))).thenAnswer(i -> i.getArgument(0));


        CourseDto patchedCourseDto = courseService.patchCourseById(courseId, authorId, updateCourseDto);


        verify(courseRepository, times(1)).findById(courseId);
        verify(accessHelper, times(1)).checkUserHasAccessToCourse(courseId, authorId);
        assertEquals(patchedCourseDto.getTitle(), "old");
        assertEquals(patchedCourseDto.getPrice(), price);
        assertEquals(patchedCourseDto.getCategory(), category);
        assertEquals(patchedCourseDto.getDescription(), description);
        assertEquals(patchedCourseDto.getStatus(), status);
        assertEquals(patchedCourseDto.getDifficulty(), difficulty);
        assertEquals(patchedCourseDto.getDuration(), duration);
        assertEquals(patchedCourseDto.getModules().size(), 1);
        ModuleDto responseModuleDto = patchedCourseDto.getModules().get(0);
        assertNull(responseModuleDto.getId());
        assertEquals(responseModuleDto.getCourseId(), courseId);
        assertNull(responseModuleDto.getCreationTime());
        assertEquals(responseModuleDto.getTitle(), moduleTitle);
        assertEquals(responseModuleDto.getDescription(), moduleDescription);

    }
}