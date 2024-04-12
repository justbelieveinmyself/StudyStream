package com.justbelieveinmyself.courseservice.domains.dtos;

import com.justbelieveinmyself.courseservice.domains.entities.Enrollment;
import com.justbelieveinmyself.courseservice.domains.enums.EnrollmentStatus;
import com.justbelieveinmyself.library.dto.Dto;
import com.justbelieveinmyself.library.dto.ModelUtils;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
@Getter
@Setter
public class EnrollmentDto implements Dto<Enrollment> {
    private Long id;
    private Long userId;
    private Long courseId;
    private Instant enrollmentTime;
    private EnrollmentStatus status;

    @Override
    public EnrollmentDto fromEntity(Enrollment entity) {
        EnrollmentDto dto = new EnrollmentDto();
        ModelUtils.copyProperties(entity, dto);

        dto.setUserId(entity.getUser().getId());
        dto.setCourseId(entity.getCourse().getId());

        return dto;
    }

    /**
     * @return Enrollment Entity without id, userId, courseId, enrollmentTime
     */
    @Override
    public Enrollment toEntity() {
        Enrollment enrollment = new Enrollment();
        ModelUtils.copyProperties(this, enrollment, "enrollmentTime");
        return enrollment;
    }

}
