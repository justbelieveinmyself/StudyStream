package com.justbelieveinmyself.courseservice.services;

import com.justbelieveinmyself.courseservice.domains.dtos.ModuleDto;
import com.justbelieveinmyself.courseservice.domains.dtos.UpdateModuleDto;
import com.justbelieveinmyself.courseservice.domains.entities.Course;
import com.justbelieveinmyself.courseservice.domains.entities.Module;
import com.justbelieveinmyself.courseservice.domains.entities.User;
import com.justbelieveinmyself.courseservice.repositories.ModuleRepository;
import com.justbelieveinmyself.library.exception.ForbiddenException;
import com.justbelieveinmyself.library.exception.NotFoundException;
import com.justbelieveinmyself.library.exception.UnprocessableEntityException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ModuleService {
    private final ModuleRepository moduleRepository;
    private final CourseService courseService;
    private final UserService userService;

    public Module findById(Long moduleId) {
        return moduleRepository.findById(moduleId).orElseThrow(() ->
                new NotFoundException("Module not found with ModuleID: " + moduleId));
    }

    public ModuleDto getModuleById(Long moduleId) {
        Module module = findById(moduleId);
        return new ModuleDto().fromEntity(module);
    }

    public ModuleDto createNewModule(ModuleDto moduleDto, Long courseId, Long userId) {
        Course course = courseService.findById(courseId);

        if (!course.getAuthor().getId().equals(userId)) {
            throw new ForbiddenException("Only the author can edit his course!");
        }

        Module module = moduleDto.toEntity();
        module.setCourse(course);
        Module savedModule = moduleRepository.save(module);

        return new ModuleDto().fromEntity(savedModule);
    }

    public void deleteModuleById(Long moduleId, Long userId) {
        Module module = findById(moduleId);
        if (!module.getCourse().getAuthor().getId().equals(userId)) {
            throw new ForbiddenException("Only the author can edit his course!");
        }
        moduleRepository.delete(module);
    }

    public ModuleDto updateModuleById(Long moduleId, Long userId, UpdateModuleDto updateModuleDto) {
        Module module = findById(moduleId);

        if (!module.getCourse().getAuthor().getId().equals(userId)) {
            throw new ForbiddenException("Only the author can edit his course!");
        }

        BeanUtils.copyProperties(updateModuleDto, module);
        Module savedModule = moduleRepository.save(module);

        return new ModuleDto().fromEntity(savedModule);
    }
}
