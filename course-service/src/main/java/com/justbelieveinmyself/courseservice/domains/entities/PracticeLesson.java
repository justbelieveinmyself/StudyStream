package com.justbelieveinmyself.courseservice.domains.entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@DiscriminatorValue("PRACTICE")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PracticeLesson extends Lesson {
    private String instruction;
}
