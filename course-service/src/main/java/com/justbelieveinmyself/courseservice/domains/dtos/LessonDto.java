package com.justbelieveinmyself.courseservice.domains.dtos;

import java.time.ZonedDateTime;

public abstract class LessonDto { //TODO
    private Long id;
    private String title;
    private String description;
    private Integer order;
    private ZonedDateTime creationTime;
    private ZonedDateTime deadLine;
    private String lessonType;
}
