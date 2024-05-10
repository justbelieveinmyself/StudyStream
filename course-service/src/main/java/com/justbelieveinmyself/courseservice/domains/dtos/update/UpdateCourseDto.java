package com.justbelieveinmyself.courseservice.domains.dtos.update;

import com.justbelieveinmyself.courseservice.domains.dtos.ModuleDto;
import com.justbelieveinmyself.courseservice.domains.enums.CourseDifficulty;
import com.justbelieveinmyself.courseservice.domains.enums.CourseStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class UpdateCourseDto {
    @NotBlank(message = "Please, enter title!")
    private String title;
    private String description;
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
    @NotNull(message = "Please, enter status! ACTIVE or ARCHIVED")
    private CourseStatus status;
    private List<ModuleDto> modules;
}
