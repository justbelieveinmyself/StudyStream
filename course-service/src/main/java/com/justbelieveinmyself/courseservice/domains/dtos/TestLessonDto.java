package com.justbelieveinmyself.courseservice.domains.dtos;

import com.justbelieveinmyself.courseservice.domains.entities.TestLesson;
import com.justbelieveinmyself.courseservice.domains.entities.TestQuestion;
import com.justbelieveinmyself.library.dto.Dto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.OneToMany;
import org.springframework.beans.BeanUtils;

import java.util.List;

public class TestLessonDto extends LessonDto implements Dto<TestLesson> {
    private Integer durationMinutes;
    private List<TestQuestionDto> questions;

    @Override
    public TestLessonDto fromEntity(TestLesson entity) {
        TestLessonDto dto = new TestLessonDto();
        dto.setLessonType("TEST");
        dto.setModuleId(entity.getModule().getId());
        return dto;
    }

    /**
     * @return TestLesson Entity without Module
     */
    @Override
    public TestLesson toEntity() {
        TestLesson entity = new TestLesson();
        BeanUtils.copyProperties(this, entity);
        return entity;
    }

}
