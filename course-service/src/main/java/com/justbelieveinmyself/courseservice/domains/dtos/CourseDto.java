package com.justbelieveinmyself.courseservice.domains.dtos;

import com.justbelieveinmyself.courseservice.domains.entities.Course;
import com.justbelieveinmyself.courseservice.domains.enums.CourseDifficulty;
import com.justbelieveinmyself.courseservice.domains.enums.CourseStatus;
import com.justbelieveinmyself.library.dto.Dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class CourseDto implements Dto<Course> {
    private Long id;
    @NotBlank(message = "Please, enter title!")
    private String title;
    private String description;
    private Long authorId;
    @NotBlank(message = "Please, enter category!")
    private String category;
    private CourseDifficulty difficulty;
    @NotBlank(message = "Please, enter duration in hours!")
    private Integer duration;
    @NotBlank(message = "Please, enter price!")
    private BigDecimal price;
    private Double rating;
    @NotBlank(message = "Please, enter status! ACTIVE or ARCHIVED")
    private CourseStatus status;
    private List<ModuleDto> modules;

    @Override
    public CourseDto fromEntity(Course course) {
        CourseDto courseDto = new CourseDto();
        BeanUtils.copyProperties(course, courseDto);
        courseDto.setAuthorId(course.getAuthor().getId());
        courseDto.setModules(course.getModules().stream().map((module) -> new ModuleDto().fromEntity(module)).collect(Collectors.toList()));
        return courseDto;
    }

    @Override
    public Course toEntity() {
        Course course = new Course();
        BeanUtils.copyProperties(this, course);
        course.setModules(this.getModules().stream().map(ModuleDto::toEntity).collect(Collectors.toList()));
        //author?
        return course;
    }

}
