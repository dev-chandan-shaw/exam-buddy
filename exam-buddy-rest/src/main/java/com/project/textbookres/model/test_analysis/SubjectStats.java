package com.project.textbookres.model.test_analysis;

import com.project.textbookres.model.Subject;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class SubjectStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @ManyToOne
    private Subject subject;
    private int totalQuestions;
    private int totalMarks;
    private double marksObtained;
    private int timeTakenSeconds;
    private int totalTime;
    private int totalCorrectQuestions;
    private int totalAttemptedQuestions;
    private int accuracy;
}
