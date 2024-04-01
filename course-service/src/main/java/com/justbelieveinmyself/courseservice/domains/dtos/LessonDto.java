package com.justbelieveinmyself.courseservice.domains.dtos;

import com.justbelieveinmyself.courseservice.domains.entities.Lesson;
import com.justbelieveinmyself.library.dto.Dto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.ZonedDateTime;

@Getter
@Setter
public abstract class LessonDto implements Dto<Lesson> {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    private String title;
    private String description;
    private Integer order;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Instant creationTime;
    private Instant deadLine;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long moduleId;
    private String lessonType;
}
