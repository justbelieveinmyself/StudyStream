package com.justbelieveinmyself.courseservice.domains.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public class LessonDto {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    private String title;
    private String description;
    private Integer order;
    private ZonedDateTime creationTime;
    private ZonedDateTime deadLine;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long moduleId;
    private String lessonType; // wtf?
}
