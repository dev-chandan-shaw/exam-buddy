package com.project.textbookres.dto.test_solution;

import lombok.Data;

import java.util.List;

@Data
public class TestSolution {
    private long testAttemptId;
    private String testName;
    private long testId;
    private List<TestSolutionSection> sections;
}
