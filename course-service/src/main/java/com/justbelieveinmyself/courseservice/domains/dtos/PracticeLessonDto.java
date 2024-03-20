package com.justbelieveinmyself.courseservice.domains.dtos;

import com.justbelieveinmyself.courseservice.domains.entities.Lesson;
import com.justbelieveinmyself.courseservice.domains.entities.PracticeLesson;
import com.justbelieveinmyself.library.dto.Dto;
import org.springframework.beans.BeanUtils;

public class PracticeLessonDto extends LessonDto implements Dto<PracticeLesson> {
    private String description;
    private String instruction;

    @Override
    public PracticeLessonDto fromEntity(PracticeLesson entity) {
        PracticeLessonDto dto = new PracticeLessonDto();
        BeanUtils.copyProperties(entity, dto);
        dto.setLessonType("PRACTICE");
        dto.setModuleId(entity.getModule().getId());
        return dto;
    }

    /**
     * @return PracticeLesson without Module
     */
    @Override
    public PracticeLesson toEntity() {
        PracticeLesson entity = new PracticeLesson();
        BeanUtils.copyProperties(this, entity);
        return entity;
    }

}
