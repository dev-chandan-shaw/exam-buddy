package com.project.textbookres.controller;

import com.project.textbookres.dto.test_solution.TestSolution;
import com.project.textbookres.model.TestAttempt;
import com.project.textbookres.respository.TestAttemptRepository;
import com.project.textbookres.service.TestSolutionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/test-solution")
public class TestSolutionController {
    @Autowired
    private TestSolutionService testSolutionService;

    @Autowired
    private TestAttemptRepository testAttemptRepository;
    @GetMapping("/by-test-attempt")
    public ResponseEntity<TestSolution> getTestAttemptSolution(@RequestParam long testAttemptId) {
        return ResponseEntity.ok(testSolutionService.getTestSolution(testAttemptId));
    }

    @GetMapping("/by-test")
    public ResponseEntity<?> getTestSolutionByTest(@RequestParam long testId) {
        List<TestAttempt> testAttempt = testAttemptRepository.findByTestIdAndUserId(testId, 1L) ; // <- replace 1L later dynamically
        long testAttemptId = testAttempt.get(testAttempt.size() - 1).getId();
        return ResponseEntity.ok(testSolutionService.getTestSolution(testAttemptId));
    }
}
