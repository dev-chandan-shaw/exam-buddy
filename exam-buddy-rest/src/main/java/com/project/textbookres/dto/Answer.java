package com.project.textbookres.dto;

import com.project.textbookres.model.Question;
import lombok.Data;

@Data
public class Answer {
    private Question question;
    private int selectedOption;
    private int timeTaken;
    private AnswerState answerState;

    public Answer(Question question, int selectedOption, int timeTaken, AnswerState answerState) {
        this.question = question;
        this.selectedOption = selectedOption;
        this.timeTaken = timeTaken;
        this.answerState = answerState;
    }
}
