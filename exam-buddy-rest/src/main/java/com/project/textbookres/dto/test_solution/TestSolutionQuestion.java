package com.project.textbookres.dto.test_solution;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.textbookres.model.Question;
import lombok.Data;

@Data
public class TestSolutionQuestion {
    private Question question;
    private int timeTakenSeconds;
    private long selectedOptionId;
    private TestSolutionQuestionStatus status;
    @JsonProperty("isQuestionSaved")
    private boolean questionSaved;
}
