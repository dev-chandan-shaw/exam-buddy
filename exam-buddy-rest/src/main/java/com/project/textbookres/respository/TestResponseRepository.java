package com.project.textbookres.respository;

import com.project.textbookres.model.TestResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TestResponseRepository extends JpaRepository<TestResponse, Long> {
    Optional<TestResponse> findByUserIdAndTestFinished(Long userId, boolean testFinished);
    List<TestResponse> findByUserIdAndTestId(Long userId, Long testId);
}
