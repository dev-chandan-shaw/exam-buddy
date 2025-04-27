package com.project.textbookres.model.test_analysis;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class TopicQuestionStats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private int questionNo;
    @JsonProperty("isAttempted")
    private boolean attempted;
    @JsonProperty("isCorrect")
    private boolean correct;
}
