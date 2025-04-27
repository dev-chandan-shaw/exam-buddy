package com.project.textbookres.respository;

import com.project.textbookres.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestRepository extends JpaRepository<Test, Long> {
    List<Test> findByExamId(Long examId);
    List<Test> findByExamIdAndUserId(Long examId, Long userId);
    List<Test> findByPublished(boolean published);

    List<Test> findByUserIdAndPublished(long userId, boolean published);
}
