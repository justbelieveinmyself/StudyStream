package com.justbelieveinmyself.courseservice.domains.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.ZonedDateTime;
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
    @ManyToOne
    @JoinColumn(nullable = false, name = "customer_id")
    private User author;
    @ManyToMany
    @JoinTable(name = "course_author", joinColumns = @JoinColumn(name = "course_id"), inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<User> subscribers;
    // modules?
}
