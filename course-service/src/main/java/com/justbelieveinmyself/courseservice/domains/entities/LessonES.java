package com.justbelieveinmyself.courseservice.domains.entities;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.Instant;


@Document(indexName = "lessons")
@Getter
@Setter
public class LessonES {
    @Id
    private Long id;
    private String title;
    private String description;
    private Integer order;
    private Instant creationTime;
    private Instant deadLine;
}
