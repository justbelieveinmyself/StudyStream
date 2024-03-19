package com.justbelieveinmyself.courseservice.domains.dtos;

import com.justbelieveinmyself.courseservice.domains.entities.Module;
import com.justbelieveinmyself.library.dto.Dto;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;
import java.util.List;
@Getter
@Setter
public class ModuleDto implements Dto<Module> {
    private Long id;
    private String title;
    private String description;
    private ZonedDateTime creationTime;
    private List<LessonDto> lessons;
    @Override
    public ModuleDto fromEntity(Module entity) {
        return null;
    }

    @Override
    public Module toEntity() {
        return null;
    }
}
