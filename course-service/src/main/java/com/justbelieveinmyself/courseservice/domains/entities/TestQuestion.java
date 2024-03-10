package com.justbelieveinmyself.courseservice.domains.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "test_questions")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class TestQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "question_id_seq")
    @SequenceGenerator(name = "question_id_seq", sequenceName = "question_id_seq")
    private Long id;
    @Column(nullable = false)
    private String text;
    @ElementCollection
    @CollectionTable(name = "test_question_options", joinColumns = @JoinColumn(name = "question_id", nullable = false))
    private List<String> options;
    @Column(nullable = false)
    private Integer correctOptionIndex;
}
