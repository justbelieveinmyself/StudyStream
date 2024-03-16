package com.justbelieveinmyself.courseservice.domains.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "users")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_seq")
    @SequenceGenerator(name = "user_id_seq", sequenceName = "user_id_seq", allocationSize = 1)
    private Long id;
    @OneToMany(mappedBy = "author")
    private Set<Course> createdCourses;
    @OneToOne(mappedBy = "user")
    private Enrollment enrollment;
}
