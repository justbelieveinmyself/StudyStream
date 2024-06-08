package com.justbelieveinmyself.courseservice.domains.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "modules")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "module_id_seq")
    @SequenceGenerator(name = "module_id_seq", sequenceName = "module_id_seq", allocationSize = 1)
    private Long id;
    private String title;
    private String description;
    @CreationTimestamp
    private Instant creationTime;
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, name = "course_id")
    private Course course;
    @OneToMany(mappedBy = "module", cascade = CascadeType.ALL)
    @Builder.Default
    private List<Lesson> lessons = new ArrayList<>();

    public void addLesson(Lesson lesson) {
        lesson.setModule(this);
        this.lessons.add(lesson);
    }
}
