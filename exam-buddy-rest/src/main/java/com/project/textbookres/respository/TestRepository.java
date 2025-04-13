package com.project.textbookres.respository;

import com.project.textbookres.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestRepository extends JpaRepository<Test, Long> {
    List<Test> findByExamId(Long examId);
}
