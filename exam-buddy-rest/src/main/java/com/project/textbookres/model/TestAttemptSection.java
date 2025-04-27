package com.project.textbookres.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class TestAttemptSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int timeTakenSeconds;

    private double marksObtained;

    private int totalMarks;

    private int totalTime;

    private int totalAttemptedQuestions;

    private int accuracy;

    @ManyToOne
    private Subject subject;

    @OneToMany
    private List<TestAttemptQuestionState> questions = new ArrayList<>();
}
