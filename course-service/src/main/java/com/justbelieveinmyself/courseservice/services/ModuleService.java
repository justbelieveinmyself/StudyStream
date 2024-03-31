package com.justbelieveinmyself.courseservice.services;

import com.justbelieveinmyself.courseservice.domains.dtos.ModuleDto;
import com.justbelieveinmyself.courseservice.domains.dtos.UpdateModuleDto;
import com.justbelieveinmyself.courseservice.domains.entities.Course;
import com.justbelieveinmyself.courseservice.domains.entities.Module;
import com.justbelieveinmyself.courseservice.repositories.ModuleRepository;
import com.justbelieveinmyself.library.exception.ForbiddenException;
import com.justbelieveinmyself.library.exception.NotFoundException;
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

    private void checkUserHasAccessToCourse(Long courseId, Long userId) {
        if (!courseService.existByIdAndAuthorId(courseId, userId)) {
            throw new ForbiddenException("Only the author can edit his course!");
        }
    }

    public ModuleDto getModuleById(Long moduleId) {
        Module module = findById(moduleId);
        return new ModuleDto().fromEntity(module);
    }

    public ModuleDto createNewModule(ModuleDto moduleDto, Long courseId, Long userId) {
        Course course = courseService.findById(courseId);

        checkUserHasAccessToCourse(courseId, userId);

        Module module = moduleDto.toEntity();
        module.setCourse(course);
        Module savedModule = moduleRepository.save(module);

        return new ModuleDto().fromEntity(savedModule);
    }

    public void deleteModuleById(Long courseId, Long moduleId, Long userId) {
        Module module = findById(moduleId);

        checkUserHasAccessToCourse(courseId, userId);

        moduleRepository.delete(module);
    }

    public ModuleDto updateModuleById(Long courseId, Long moduleId, Long userId, UpdateModuleDto updateModuleDto) {
        Module module = findById(moduleId);

        checkUserHasAccessToCourse(courseId, userId);

        BeanUtils.copyProperties(updateModuleDto, module);
        Module savedModule = moduleRepository.save(module);

        return new ModuleDto().fromEntity(savedModule);
    }
}
