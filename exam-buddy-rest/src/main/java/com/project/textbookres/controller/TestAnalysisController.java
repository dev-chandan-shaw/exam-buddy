package com.project.textbookres.controller;

import com.project.textbookres.model.test_analysis.TestAnalysis;
import com.project.textbookres.respository.TestAnalysisRepository;
import com.project.textbookres.service.TestAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/test-analysis")
public class TestAnalysisController {

    @Autowired
    private TestAnalysisRepository testAnalysisRepository;

    @Autowired
    private TestAnalysisService service;
    @GetMapping
    public TestAnalysis getTestAnalysis(@RequestParam long testAttemptId) {
        return service.getTestAnalysis(testAttemptId);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAllTestAnalysis() {
        testAnalysisRepository.deleteAll();
        return ResponseEntity.ok("All test analysis deleted successfully");
    }
}
