package com.justbelieveinmyself.courseservice.domains.entities;

import com.justbelieveinmyself.courseservice.domains.enums.CourseDifficulty;
import com.justbelieveinmyself.courseservice.domains.enums.CourseStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "courses")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_id_seq")
    @SequenceGenerator(name = "course_id_seq", sequenceName = "course_id_seq", allocationSize = 1)
    private Long id;
    private String title;
    private String description;
    @CreationTimestamp
    private ZonedDateTime creationTime;
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, name = "author_id")
    private User author;
    private String category;
    @Enumerated(EnumType.STRING)
    private CourseDifficulty difficulty;
    private Integer duration;
    private BigDecimal price;
    private Double rating;
    @Enumerated(EnumType.STRING)
    private CourseStatus status;
    @OneToMany(mappedBy = "course", orphanRemoval = true)
    private List<Module> modules;
    @OneToOne(mappedBy = "course")
    private Enrollment enrollment;
}
