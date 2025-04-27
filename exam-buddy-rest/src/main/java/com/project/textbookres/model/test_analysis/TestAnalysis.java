package com.project.textbookres.model.test_analysis;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class TestAnalysis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String testName;
    private int rankOfUser;
    private int rankOutOf;
    private double percentile;
    private long testAttemptId;
    @OneToMany
    private List<SubjectStats> subjectStats;
    @OneToMany
    private List<TopicAnalysis> topicsAnalysis;
}
