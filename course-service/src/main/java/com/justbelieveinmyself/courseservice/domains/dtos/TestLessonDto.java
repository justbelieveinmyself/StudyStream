package com.justbelieveinmyself.courseservice.domains.dtos;

import com.justbelieveinmyself.courseservice.domains.entities.Lesson;
import com.justbelieveinmyself.courseservice.domains.entities.TestLesson;
import com.justbelieveinmyself.courseservice.domains.entities.TestQuestion;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TestLessonDto extends LessonDto {
    @NotNull(message = "Please, enter [durationMinutes] of test lesson!")
    @Min(value = 1, message = "Lesson durationMinutes cannot be smaller than 1!")
    private Integer durationMinutes;
    @NotEmpty(message = "TestLesson [questions] is empty!")
    private List<TestQuestionDto> questions;

    @Override
    public LessonDto fromEntity(Lesson entity) {
        TestLesson specifiedEntity = (TestLesson) entity;
        TestLessonDto dto = new TestLessonDto();

        BeanUtils.copyProperties(entity, dto);
        dto.setLessonType("TEST");

        if (entity.getModule() != null) {
            dto.setModuleId(entity.getModule().getId());
        }

        List<TestQuestionDto> testQuestions = specifiedEntity.getQuestions()
                .stream()
                .map((question -> new TestQuestionDto().fromEntity(question)))
                .collect(Collectors.toList());
        dto.setQuestions(testQuestions);

        return dto;
    }

    /**
     * @return TestLesson Entity without module, id, creationTime
     */
    @Override
    public TestLesson toEntity() {
        TestLesson entity = new TestLesson();
        BeanUtils.copyProperties(this, entity);

        List<TestQuestion> questions = new ArrayList<>();

        for (TestQuestionDto questionDto : this.getQuestions()) {
            TestQuestion question = questionDto.toEntity();
            question.setLesson(entity);
            questions.add(question);
        }

        entity.setQuestions(questions);

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
