package com.justbelieveinmyself.courseservice.domains.dtos;

import com.justbelieveinmyself.courseservice.domains.entities.Lesson;
import com.justbelieveinmyself.courseservice.domains.entities.Module;
import com.justbelieveinmyself.courseservice.domains.entities.PracticeLesson;
import com.justbelieveinmyself.courseservice.domains.entities.TestLesson;
import com.justbelieveinmyself.library.dto.Dto;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang.NotImplementedException;
import org.springframework.beans.BeanUtils;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ModuleDto implements Dto<Module> {
    private Long id;
    private String title;
    private String description;
    private ZonedDateTime creationTime;
    private Long courseId;
    private List<LessonDto> lessons;

    @Override
    public ModuleDto fromEntity(Module entity) {
        ModuleDto moduleDto = new ModuleDto();
        BeanUtils.copyProperties(entity, moduleDto);
        moduleDto.setLessons(entity.getLessons().stream().map(lesson -> {
            if (lesson instanceof PracticeLesson) {
                return new PracticeLessonDto().fromEntity((PracticeLesson) lesson);
            } else if (lesson instanceof TestLesson) {
                return new TestLessonDto().fromEntity((TestLesson) lesson);
            } else {
                throw new IllegalArgumentException("Unknown type of lesson: " + lesson.getClass().getSimpleName());
            }
        }).collect(Collectors.toList()));
        moduleDto.setCourseId(entity.getCourse().getId());
        return moduleDto;
    }

    /**
     * @return Module Entity without Author
     */
    @Override
    public Module toEntity() {
        Module entity = new Module();
        BeanUtils.copyProperties(this, entity);
        entity.setLessons(this.getLessons().stream().map((lessonDto -> {
            if (lessonDto instanceof PracticeLessonDto) {
                return ((PracticeLessonDto) lessonDto).toEntity();
            } else if (lessonDto instanceof TestLessonDto) {
                return ((TestLessonDto) lessonDto).toEntity();
            } else {
                throw new IllegalArgumentException("Unknown type of lesson: " + lessonDto.getClass().getSimpleName());
            }
        })).collect(Collectors.toList()));
        return entity;
    } //TODO: add body to lesson

}
