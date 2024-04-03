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
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class CourseDto implements Dto<Course> {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    @NotBlank(message = "Please, enter [title] of course!")
    private String title;
    private String description;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Instant creationTime;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long authorId;
    @NotBlank(message = "Please, enter [category] of course!")
    private String category;
    @NotNull(message = "Please, enter [difficulty] of course! BEGINNER, INTERMEDIATE or ADVANCED")
    private CourseDifficulty difficulty;
    @NotNull(message = "Please, enter [duration] of course in hours!")
    @Min(value = 1, message = "Duration of course cannot be small than 1h!")
    private Integer duration;
    @NotNull(message = "Please, enter [price] of course!")
    @Min(value = 0, message = "Price of course cannot be small than 0!")
    private BigDecimal price;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Double rating;
    @NotNull(message = "Please, enter [status] of course! ACTIVE or ARCHIVED")
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
     * @return Course Entity without id, author, rating, creationTime,
     */
    @Override
    public Course toEntity() {
        Course course = new Course();
        BeanUtils.copyProperties(this, course);

        List<Module> modules = new ArrayList<>();

        for (ModuleDto moduleDto : this.getModules()) {
            Module module = moduleDto.toEntity();
            module.setCourse(course);
            modules.add(module);
        }

        course.setModules(modules);

        course.setId(null);
        course.setRating(null);
        course.setCreationTime(null);

        return course;
    }

}
