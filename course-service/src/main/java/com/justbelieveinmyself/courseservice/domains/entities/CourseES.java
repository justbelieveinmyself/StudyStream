package com.justbelieveinmyself.courseservice.domains.entities;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

@Document(indexName = "courses")
@Getter
@Setter
public class CourseES {
    @Id
    private Long id;
    @Field(type = FieldType.Keyword)
    private String title;
    private String description;
    private Instant creationTime;
    private User author; // Предполагаем, что это имя автора
    private String category;
    private String difficulty;
    private Integer duration;
    private BigDecimal price;
    private Double rating;
    private String status;
    private List<ModuleES> modules;
}
