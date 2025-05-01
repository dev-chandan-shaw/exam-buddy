package com.project.textbookres.dto.test_solution;

import com.project.textbookres.model.Subject;
import lombok.Data;

import java.util.List;

@Data
public class TestSolutionSection {
    private Subject subject;
    private List<TestSolutionQuestion> questions;
}
