package com.justbelieveinmyself.courseservice.domains.entities;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;

import java.time.Instant;
import java.util.List;

@Document(indexName = "modules")
@Getter
@Setter
class ModuleES {
    @Id
    private Long id;
    private String title;
    private String description;
    private Instant creationTime;
    private List<LessonES> lessons;

    // Getters and setters
}