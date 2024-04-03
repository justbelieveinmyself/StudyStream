package com.justbelieveinmyself.courseservice.services;

import com.justbelieveinmyself.courseservice.domains.dtos.LessonDto;
import com.justbelieveinmyself.courseservice.domains.dtos.ModuleDto;
import com.justbelieveinmyself.courseservice.domains.dtos.UpdateLessonDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LessonService { //TODO: implement methods
    public LessonDto getLessonById(Long lessonId) {
        return null;
    }

    public LessonDto createNewLesson(LessonDto lessonDto, Long courseId, Long userId) {
        return null;
    }

    public void deleteLessonById(Long courseId, Long lessonId, Long userId) {

    }

    public LessonDto updateLessonById(Long courseId, Long moduleId, Long userId, UpdateLessonDto updateLessonDto) {
        return null;
    }

    public ModuleDto patchLessonById(Long courseId, Long moduleId, Long userId, UpdateLessonDto updateLessonDto) {
        return null;
    }
}
