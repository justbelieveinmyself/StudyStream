package com.justbelieveinmyself.courseservice.services;

import com.justbelieveinmyself.courseservice.domains.dtos.LessonDto;
import com.justbelieveinmyself.courseservice.domains.entities.Lesson;
import com.justbelieveinmyself.courseservice.domains.entities.Module;
import com.justbelieveinmyself.courseservice.domains.entities.TestLesson;
import com.justbelieveinmyself.courseservice.helpers.AccessHelper;
import com.justbelieveinmyself.courseservice.repositories.LessonRepository;
import com.justbelieveinmyself.library.dto.ModelUtils;
import com.justbelieveinmyself.library.exception.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class LessonService {
    private final LessonRepository lessonRepository;
    private final ModuleService moduleService;
    private final AccessHelper accessHelper;

    public Lesson findById(Long lessonId) {
        return lessonRepository.findById(lessonId).orElseThrow(() -> new NotFoundException("Lesson not found with LessonID: " + lessonId));
    }

    public LessonDto getLessonById(Long lessonId) {
        Lesson lesson = findById(lessonId);
        return LessonDto.createLessonDto(lesson);
    }

    public LessonDto createNewLesson(LessonDto lessonDto, Long courseId, Long moduleId, Long userId) {
        accessHelper.checkUserHasAccessToCourse(courseId, userId);
        Module module = moduleService.findById(moduleId);

        Lesson lesson = lessonDto.toEntity();
        lesson.setModule(module);
        Lesson savedLesson = lessonRepository.save(lesson);

        return lessonDto.fromEntity(savedLesson);
    }

    public void deleteLessonById(Long courseId, Long lessonId, Long userId) {
        accessHelper.checkUserHasAccessToCourse(courseId, userId);
        lessonRepository.deleteById(lessonId);
    }

    @Transactional
    public LessonDto updateLessonById(Long courseId, Long lessonId, Long userId, LessonDto lessonDto) {
        accessHelper.checkUserHasAccessToCourse(courseId, userId);
        Lesson lessonDest = findById(lessonId);
        Lesson lessonSrc = lessonDto.toEntity();
        /*if (!lessonDest.getClass().equals(lessonSrc.getClass())) { //TODO: switch classes for lesson on update?
            Lesson lessonTemp = lessonDest;
            if (lessonSrc instanceof PracticeLesson) {
                lessonDest = new PracticeLesson();
            } else {
                lessonDest = new TestLesson();
            }
            BeanUtils.copyProperties(lessonTemp, lessonDest);
        }*/
        ModelUtils.copyProperties(lessonSrc, lessonDest, "creationTime", "id", "module", "questions");
        if (lessonDest instanceof TestLesson) {
            TestLesson testLesson = (TestLesson) lessonDest;
            testLesson.getQuestions().clear();
            if (lessonDto.getLessonType().equals("TEST")) {
                TestLesson testLessonSrc = (TestLesson) lessonSrc;
                testLessonSrc.getQuestions().forEach(testQuestion -> {
                    testLesson.getQuestions().add(testQuestion);
                    testQuestion.setLesson(testLesson);
                });
            }
        }
        Lesson savedLesson = lessonRepository.save(lessonDest);
        return LessonDto.createLessonDto(savedLesson);
    }


    public LessonDto patchLessonById(Long courseId, Long lessonId, Long userId, LessonDto lessonDto) {
        accessHelper.checkUserHasAccessToCourse(courseId, userId);
        Lesson lesson = findById(lessonId);


        //TODO: null checking and assign
        lessonRepository.save(lesson);
        return LessonDto.createLessonDto(lesson);
    }

    private void updateLessonFields(LessonDto lessonDto, Lesson lesson) {
        Lesson fromDto = lessonDto.toEntity();

        ModelUtils.copyPropertiesIgnoreNull(lessonDto, lesson);

        if (lessonDto.getLessonType().equals("TEST") && lesson instanceof TestLesson) {
            TestLesson testLesson = (TestLesson) lesson;
            testLesson.getQuestions().clear();
            TestLesson testLessonFromDto = (TestLesson) fromDto;

            testLessonFromDto.getQuestions().forEach(testQuestion -> {
                testLesson.getQuestions().add(testQuestion);
                testQuestion.setLesson(testLesson);
            });

        }
    }

}
//TODO: enrollment service with mail to subscriber
//TODO: question service for testquestions? get post put patch....
//TODO: object storage for images/videos etc..