package com.justbelieveinmyself.courseservice.domains.dtos.update;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
public abstract class UpdateLessonDto {
    private String title;
    private String description;
    private Integer order;
    private Instant deadLine;
    private String lessonType;
}
