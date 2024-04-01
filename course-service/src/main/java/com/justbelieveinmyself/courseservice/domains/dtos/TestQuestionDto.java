package com.justbelieveinmyself.courseservice.domains.dtos;

import com.justbelieveinmyself.courseservice.domains.entities.TestQuestion;
import com.justbelieveinmyself.library.dto.Dto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.List;
@Getter
@Setter
public class TestQuestionDto implements Dto<TestQuestion> {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    @NotBlank(message = "Please, enter [text] of test question!")
    private String text;
    @NotEmpty(message = "Please, enter answer [options] of test question!")
    private List<String> options;
    @NotNull(message = "Please, enter [correctOptionIndex] of test question!")
    private Integer correctOptionIndex;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long lessonId;

    @Override
    public TestQuestionDto fromEntity(TestQuestion entity) {
        TestQuestionDto dto = new TestQuestionDto();
        BeanUtils.copyProperties(entity, dto);

        if (entity.getLesson() != null) {
            dto.setLessonId(entity.getLesson().getId());
        }

        return dto;
    }

    /**
     * @return TestQuestion Entity without id, lesson
     */
    @Override
    public TestQuestion toEntity() {
        TestQuestion entity = new TestQuestion();
        BeanUtils.copyProperties(this, entity);

        entity.setId(null);

        return entity;
    }
}
