package com.project.textbookres.dto;

import lombok.Data;

@Data
public class SaveQuestionRequest {
    private long testId;
    private long questionId;
}
