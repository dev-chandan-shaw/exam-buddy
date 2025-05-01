package com.project.textbookres.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String passage;

    private String description;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    private QuestionStats questionStats = new QuestionStats();

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<QuestionOption> options;

    @ManyToOne
    @JoinColumn(name = "subtopic_id")
    private Subtopic subtopic;

    @ManyToOne
    @JoinColumn(name = "test_id")
    @JsonIgnore
    private Exam exam;

}
