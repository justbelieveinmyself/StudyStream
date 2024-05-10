package com.justbelieveinmyself.courseservice.domains.dtos;

import com.justbelieveinmyself.courseservice.domains.entities.Lesson;
import com.justbelieveinmyself.courseservice.domains.entities.Module;
import com.justbelieveinmyself.courseservice.domains.entities.PracticeLesson;
import com.justbelieveinmyself.courseservice.domains.entities.TestLesson;
import com.justbelieveinmyself.library.dto.Dto;
import com.justbelieveinmyself.library.dto.ModelUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class ModuleDto implements Dto<Module> {
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long id;
    @NotBlank(message = "Please, enter [title] of module!")
    private String title;
    private String description;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Instant creationTime;
    @Schema(accessMode = Schema.AccessMode.READ_ONLY)
    private Long courseId;
    private List<LessonDto> lessons;

    @Override
    public ModuleDto fromEntity(Module entity) {
        ModuleDto moduleDto = new ModuleDto();
        ModelUtils.copyProperties(entity, moduleDto);

        if (entity.getLessons() != null) {
            moduleDto.setLessons(entity.getLessons().stream().map(lesson -> {
                if (lesson instanceof PracticeLesson) {
                    return new PracticeLessonDto().fromEntity((PracticeLesson) lesson);
                } else if (lesson instanceof TestLesson) {
                    return new TestLessonDto().fromEntity((TestLesson) lesson);
                } else {
                    throw new IllegalArgumentException("Unknown type of lesson: " + lesson.getClass().getSimpleName());
                }
            }).collect(Collectors.toList()));
        }

        if (entity.getCourse() != null) {
            moduleDto.setCourseId(entity.getCourse().getId());
        }

        return moduleDto;
    }

    /**
     * @return Module Entity without id, course, creationTime
     */
    @Override
    public Module toEntity() {
        Module entity = new Module();
        ModelUtils.copyProperties(this, entity);

        List<Lesson> lessons = new ArrayList<>();

        for (LessonDto lessonDto : this.getLessons()) {
            Lesson lesson = lessonDto.toEntity();
            lesson.setModule(entity);
            lessons.add(lesson);
        }

        entity.setLessons(lessons);

        entity.setId(null);
        entity.setCreationTime(null);

        return entity;
    }

}
