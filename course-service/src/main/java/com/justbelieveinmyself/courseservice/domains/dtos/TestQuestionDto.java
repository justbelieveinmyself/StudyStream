package com.justbelieveinmyself.courseservice.domains.dtos;

import com.justbelieveinmyself.courseservice.domains.entities.Lesson;
import com.justbelieveinmyself.courseservice.domains.entities.TestQuestion;
import com.justbelieveinmyself.library.dto.Dto;
import jakarta.persistence.*;

import java.util.List;

public class TestQuestionDto implements Dto<TestQuestion> {
    private Long id;
    private String text;
    private List<String> options;
    private Integer correctOptionIndex;
    private Long lessonId;

    @Override
    public Dto<TestQuestion> fromEntity(TestQuestion entity) {
        return null;
    }

    @Override
    public TestQuestion toEntity() {
        return null;
    }
}
