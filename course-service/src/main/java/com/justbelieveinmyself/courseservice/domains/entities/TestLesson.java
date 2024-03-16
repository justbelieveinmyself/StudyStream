package com.justbelieveinmyself.courseservice.domains.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@DiscriminatorValue("TEST")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TestLesson extends Lesson {
    private Integer durationMinutes;
    @OneToMany(mappedBy = "lesson", cascade = CascadeType.ALL)
    private List<TestQuestion> questions;
}
