package com.project.textbookres.dto;

import com.project.textbookres.model.Question;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

@Data
public class ActiveTestQuestionStateResponse {
    private long id;
    private ActiveTestQuestionResponseDto question;
    private long selectedOptionId;
    private int timeTakenSeconds;
    @Enumerated(EnumType.STRING)
    private QuestionStatus status;
}
