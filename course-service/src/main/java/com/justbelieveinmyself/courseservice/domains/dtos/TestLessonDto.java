package com.justbelieveinmyself.courseservice.domains.dtos;

import com.justbelieveinmyself.courseservice.domains.entities.Lesson;
import com.justbelieveinmyself.courseservice.domains.entities.TestLesson;
import org.springframework.beans.BeanUtils;

import java.util.List;

public class TestLessonDto extends LessonDto {
    private Integer durationMinutes;
    private List<TestQuestionDto> questions;

    @Override
    public LessonDto fromEntity(Lesson entity) {
        TestLessonDto dto = new TestLessonDto();
        BeanUtils.copyProperties(entity, dto);
        dto.setLessonType("TEST");
        if (entity.getModule() != null) {
            dto.setModuleId(entity.getModule().getId());
        }
        return dto;
    }

    /**
     * @return TestLesson Entity without moduleId, id, creationTime
     */
    @Override
    public TestLesson toEntity() {
        TestLesson entity = new TestLesson();
        BeanUtils.copyProperties(this, entity); //TODO: questions

        entity.setId(null);
        entity.setCreationTime(null);

        return entity;
    }

    public Integer getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Integer durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public List<TestQuestionDto> getQuestions() {
        return questions;
    }

    public void setQuestions(List<TestQuestionDto> questions) {
        this.questions = questions;
    }

}
