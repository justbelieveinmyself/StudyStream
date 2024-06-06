package com.justbelieveinmyself.courseservice.domains.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
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
    private Long id;
    @OneToMany(mappedBy = "author")
    @Builder.Default
    private Set<Course> createdCourses = new HashSet<>();
    @OneToOne(mappedBy = "user")
    private Enrollment enrollment;
}
