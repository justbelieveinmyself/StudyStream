package com.justbelieveinmyself.courseservice.domains.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Getter
@Setter
public abstract class LessonDto {
    private Long id;
    private String title;
    private String description;
    private Integer order;
    private ZonedDateTime creationTime;
    private ZonedDateTime deadLine;
    private Long moduleId;
    private String lessonType; // wtf?
}
