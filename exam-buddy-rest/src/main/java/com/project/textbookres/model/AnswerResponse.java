package com.project.textbookres.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.project.textbookres.dto.AnswerState;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class AnswerResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Question question;

    private int selectedOption;

    private int timeTaken;

    @Enumerated(EnumType.ORDINAL)
    private AnswerState currentState;

    @ManyToOne
    @JoinColumn(name = "test_response_id", nullable = false)
    @JsonIgnore
    private TestResponse testResponse;


    public AnswerResponse(Question question, int selectedOption, int timeTaken, AnswerState currentState) {
        this.question = question;
        this.selectedOption = selectedOption;
        this.timeTaken = timeTaken;
        this.currentState = currentState;
    }

    @Override
    public String toString() {
        return "AnswerResponse{" +
                "currentState=" + currentState +
                ", id=" + id +
                ", question=" + question.getDescription() +
                ", selectedOption=" + selectedOption +
                ", timeTaken=" + timeTaken +
                '}';
    }
}
