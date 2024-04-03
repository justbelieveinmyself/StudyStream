package com.justbelieveinmyself.courseservice.domains.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class UpdateModuleDto {
    @NotBlank(message = "Please, enter [title] of module!")
    private String title;
    @NotBlank(message = "Please, enter [description] of module!")
    private String description;
    @NotEmpty(message = "Please, enter [lessons] of module!")
    private List<LessonDto> lessons;
}
