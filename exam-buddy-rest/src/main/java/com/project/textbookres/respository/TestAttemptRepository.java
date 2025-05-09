package com.project.textbookres.respository;

import com.project.textbookres.model.TestAttempt;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TestAttemptRepository extends JpaRepository<TestAttempt, Long> {
    Optional<TestAttempt> findByUserIdAndCompleted(long userId, boolean completed);
    List<TestAttempt> findByTestIdAndUserId(long testId, long userId);

    List<TestAttempt> findByUserIdAndTestId(long userId, long testId);

    List<TestAttempt> findByTestIdOrderByMarksObtainedDesc(long testId);
}

