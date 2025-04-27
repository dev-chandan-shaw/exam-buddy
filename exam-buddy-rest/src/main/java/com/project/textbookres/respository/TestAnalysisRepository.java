package com.project.textbookres.respository;

import com.project.textbookres.model.test_analysis.TestAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public  interface TestAnalysisRepository extends JpaRepository<TestAnalysis, Long> {
    Optional<TestAnalysis> findByTestAttemptId(long testAttemptId);
}
