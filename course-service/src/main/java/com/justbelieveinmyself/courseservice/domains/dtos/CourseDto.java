package com.justbelieveinmyself.courseservice.domains.dtos;

import com.justbelieveinmyself.courseservice.domains.entities.Course;
import com.justbelieveinmyself.library.dto.Dto;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Getter
@Setter
public class CourseDto implements Dto<Course> {
    // TODO: fields, constraints
    @Override
    public CourseDto fromEntity(Course course) {
        CourseDto courseDto = new CourseDto();
        BeanUtils.copyProperties(course, courseDto);
        return courseDto;
    }

    @Override
    public Course toEntity() {
        Course course = new Course();
        BeanUtils.copyProperties(this, course);
        return course;
    }

}
