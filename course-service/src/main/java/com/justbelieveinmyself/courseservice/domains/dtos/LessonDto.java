package com.justbelieveinmyself.courseservice.domains.dtos;

import com.justbelieveinmyself.courseservice.domains.entities.Lesson;
import com.justbelieveinmyself.library.dto.Dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public abstract class LessonDto implements Dto<Lesson> {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    @NotBlank(message = "Please, enter [title] of lesson!")
    private String title;
    private String description;
    @NotBlank(message = "Please, enter [order] of lesson!")
    private Integer order;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Instant creationTime;
    @NotNull(message = "Please, enter [deadLine] of lesson!")
    private Instant deadLine;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long moduleId;
    @NotBlank(message = "Please, enter [lessonType]! ex. TEST or PRACTICE")
    @Pattern(regexp = "(TEST|PRACTICE)}")
    private String lessonType;
}
