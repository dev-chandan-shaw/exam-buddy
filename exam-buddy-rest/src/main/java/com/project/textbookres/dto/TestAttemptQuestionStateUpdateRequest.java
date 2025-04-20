package com.project.textbookres.dto;

import lombok.Getter;

@Getter
public class TestAttemptQuestionStateUpdateRequest {
    private long testAttemptQuestionStateId;
    private QuestionStatus questionStatus;
    private int timeTakenSeconds;
    private int sectionTimeTakenSeconds;
    private long selectedOptionId;
    private long testAttemptSectionId;
    private long testAttemptId;
}
