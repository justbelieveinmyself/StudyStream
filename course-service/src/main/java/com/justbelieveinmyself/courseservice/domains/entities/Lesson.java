package com.justbelieveinmyself.courseservice.domains.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;

@Entity
@Table(name = "lessons")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "lesson_type", discriminatorType = DiscriminatorType.STRING)
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public abstract class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "lesson_id_seq")
    @SequenceGenerator(name = "lesson_id_seq", sequenceName = "lesson_id_seq", allocationSize = 1)
    private Long id;
    private String title;
    private String description;
    @Column(nullable = false, name = "lesson_order")
    private Integer order;
    @CreationTimestamp
    private ZonedDateTime creationTime;
    private ZonedDateTime deadLine;
    @ManyToOne
    @JoinColumn(name = "module_id")
    private Module module;
}
