package com.justbelieveinmyself.courseservice.domains.dtos;

import com.justbelieveinmyself.courseservice.domains.entities.Course;
import com.justbelieveinmyself.courseservice.domains.entities.Module;
import com.justbelieveinmyself.courseservice.domains.enums.CourseDifficulty;
import com.justbelieveinmyself.courseservice.domains.enums.CourseStatus;
import com.justbelieveinmyself.library.dto.Dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class CourseDto implements Dto<Course> {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    @NotBlank(message = "Please, enter title!")
    private String title;
    private String description;
    private Long authorId;
    @NotBlank(message = "Please, enter category!")
    private String category;
    @NotNull(message = "Please, enter difficulty! BEGINNER, INTERMEDIATE or ADVANCED")
    private CourseDifficulty difficulty;
    @NotNull(message = "Please, enter duration in hours!")
    @Min(value = 1, message = "Duration cannot be small than 1h!")
    private Integer duration;
    @NotNull(message = "Please, enter price!")
    @Min(value = 0, message = "Price cannot be small than 0!")
    private BigDecimal price;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Double rating;
    @NotNull(message = "Please, enter status! ACTIVE or ARCHIVED")
    private CourseStatus status;
    private List<ModuleDto> modules;

    @Override
    public CourseDto fromEntity(Course course) {
        CourseDto courseDto = new CourseDto();
        BeanUtils.copyProperties(course, courseDto);
        courseDto.setAuthorId(course.getAuthor().getId());
        if (!course.getModules().isEmpty()) {
            courseDto.setModules(course.getModules().stream().map((module) -> new ModuleDto().fromEntity(module)).collect(Collectors.toList()));
        }
        return courseDto;
    }

    /**
     * @return Course Entity without Author
     */
    @Override
    public Course toEntity() {
        Course course = new Course();
        BeanUtils.copyProperties(this, course);

        for (ModuleDto moduleDto : this.getModules()) {
            Module module = moduleDto.toEntity();
            course.addModule(module);
        }

        return course;
    }

}
