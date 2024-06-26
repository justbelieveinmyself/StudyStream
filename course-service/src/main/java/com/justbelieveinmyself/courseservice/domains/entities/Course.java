package com.justbelieveinmyself.courseservice.domains.entities;

import com.justbelieveinmyself.courseservice.domains.enums.CourseDifficulty;
import com.justbelieveinmyself.courseservice.domains.enums.CourseStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "course_id_seq")
    @SequenceGenerator(name = "course_id_seq", sequenceName = "course_id_seq", allocationSize = 1)
    private Long id;
    private String title;
    private String description;
    @CreationTimestamp
    private Instant creationTime;
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
    @OneToMany(mappedBy = "course", orphanRemoval = true, cascade = CascadeType.ALL)
    @Builder.Default
    private List<Module> modules = new ArrayList<>();
    @OneToOne(mappedBy = "course")
    private Enrollment enrollment;

    public void addModule(Module module) {
        module.setCourse(this);
        this.modules.add(module);
    }

}
