package com.justbelieveinmyself.courseservice.domains.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;

@Entity
@Table(name = "modules")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "module_id_seq")
    @SequenceGenerator(name = "module_id_seq", sequenceName = "module_id_seq", allocationSize = 1)
    private Long id;
    private String title;
    private String description;
    @CreationTimestamp
    private ZonedDateTime creationTime;
    @ManyToOne(optional = false)
    @JoinColumn(nullable = false, name = "course_id")
    private Course course;
    //list of lessons? test/practice
}
