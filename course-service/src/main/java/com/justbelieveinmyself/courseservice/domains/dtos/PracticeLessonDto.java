package com.justbelieveinmyself.courseservice.domains.dtos;

import com.justbelieveinmyself.courseservice.domains.entities.Lesson;
import com.justbelieveinmyself.courseservice.domains.entities.PracticeLesson;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.BeanUtils;

public class PracticeLessonDto extends LessonDto {
    @NotBlank(message = "Please, enter [instruction] of lesson!")
    private String instruction;

    @Override
    public PracticeLessonDto fromEntity(Lesson entity) {
        PracticeLessonDto dto = new PracticeLessonDto();
        BeanUtils.copyProperties(entity, dto);
        dto.setLessonType("PRACTICE");
        if (entity.getModule() != null) {
            dto.setModuleId(entity.getModule().getId());
        }
        return dto;
    }

    /**
     * @return PracticeLesson without moduleId, id, creationTime
     */
    @Override
    public PracticeLesson toEntity() {
        PracticeLesson entity = new PracticeLesson();
        BeanUtils.copyProperties(this, entity);

        entity.setId(null);
        entity.setCreationTime(null);

        return entity;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

}
