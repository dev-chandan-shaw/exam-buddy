package com.project.textbookres.respository;

import com.project.textbookres.model.SavedQuestion;
import com.project.textbookres.model.Test;
import com.project.textbookres.model.User;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SavedQuestionRepository extends JpaRepository<SavedQuestion, Long> {
    List<SavedQuestion> findAllByUser(User user);

    List<SavedQuestion> findAllByUserAndTest(User user, @NotNull Test test);

    void deleteByQuestionIdAndUserId(long questionId, long id);
}
