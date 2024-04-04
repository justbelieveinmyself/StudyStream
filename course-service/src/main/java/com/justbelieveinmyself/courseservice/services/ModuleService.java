package com.justbelieveinmyself.courseservice.services;

import com.justbelieveinmyself.courseservice.domains.dtos.LessonDto;
import com.justbelieveinmyself.courseservice.domains.dtos.ModuleDto;
import com.justbelieveinmyself.courseservice.domains.dtos.update.UpdateModuleDto;
import com.justbelieveinmyself.courseservice.domains.entities.Course;
import com.justbelieveinmyself.courseservice.domains.entities.Lesson;
import com.justbelieveinmyself.courseservice.domains.entities.Module;
import com.justbelieveinmyself.courseservice.helpers.AccessHelper;
import com.justbelieveinmyself.courseservice.repositories.ModuleRepository;
import com.justbelieveinmyself.library.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ModuleService {
    private final ModuleRepository moduleRepository;
    private final CourseService courseService;
    private final UserService userService;
    private final AccessHelper accessHelper;

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

        accessHelper.checkUserHasAccessToCourse(courseId, userId);

        Module module = moduleDto.toEntity();
        module.setCourse(course);
        Module savedModule = moduleRepository.save(module);

        return new ModuleDto().fromEntity(savedModule);
    }

    public void deleteModuleById(Long courseId, Long moduleId, Long userId) {
        accessHelper.checkUserHasAccessToCourse(courseId, userId);

        moduleRepository.deleteById(moduleId);
    }

    public ModuleDto updateModuleById(Long courseId, Long moduleId, Long userId, UpdateModuleDto updateModuleDto) {
        Module module = findById(moduleId);

        accessHelper.checkUserHasAccessToCourse(courseId, userId);

        BeanUtils.copyProperties(updateModuleDto, module);
        setLessonsInModuleFromUpdateModuleDto(updateModuleDto, module);
        Module savedModule = moduleRepository.save(module);

        return new ModuleDto().fromEntity(savedModule);
    }

    public ModuleDto patchModuleById(Long courseId, Long moduleId, Long userId, UpdateModuleDto updateModuleDto) {
        Module module = findById(moduleId);

        accessHelper.checkUserHasAccessToCourse(courseId, userId);

        if (updateModuleDto.getTitle() != null) {
            module.setTitle(updateModuleDto.getTitle());
        }
        if (updateModuleDto.getDescription() != null) {
            module.setDescription(updateModuleDto.getDescription());
        }
        setLessonsInModuleFromUpdateModuleDto(updateModuleDto, module);
        Module savedModule = moduleRepository.save(module);

        return new ModuleDto().fromEntity(savedModule);
    }

    private void setLessonsInModuleFromUpdateModuleDto(UpdateModuleDto updateModuleDto, Module module) {
        if (updateModuleDto.getLessons() != null) {
            List<Lesson> lessons = new ArrayList<>();

            for (LessonDto lessonDto : updateModuleDto.getLessons()) {
                Lesson lesson = lessonDto.toEntity();
                module.addLesson(lesson);
                lesson.setModule(module);
                lessons.add(lesson);
            }

            module.setLessons(lessons);
        }
    }

}
