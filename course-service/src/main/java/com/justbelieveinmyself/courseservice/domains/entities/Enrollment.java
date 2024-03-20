package com.justbelieveinmyself.courseservice.domains.entities;

import com.justbelieveinmyself.courseservice.domains.enums.EnrollmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;

@Entity
@Table(name = "enrollments")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "enrollment_id_seq")
    @SequenceGenerator(name = "enrollment_id_seq", sequenceName = "enrollment_id_seq")
    private Long id;
    @OneToOne
    @JoinColumn(nullable = false, name = "user_id")
    private User user;
    @OneToOne
    @JoinColumn(nullable = false, name = "course_id")
    private Course course;
    @CreationTimestamp
    private ZonedDateTime enrollmentTime;
    @Enumerated(EnumType.STRING)
    private EnrollmentStatus status;
}