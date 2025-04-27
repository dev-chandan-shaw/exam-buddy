package com.project.textbookres.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.textbookres.dto.QuestionStatus;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class TestAttemptQuestionState {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Question question;

    @Enumerated(EnumType.STRING)
    private QuestionStatus status = QuestionStatus.NOT_VISITED;

    private long selectedOptionId;

    private int timeTakenSeconds;

    @JsonProperty("isCorrect")
    @JsonIgnore
    private boolean correct;
}
