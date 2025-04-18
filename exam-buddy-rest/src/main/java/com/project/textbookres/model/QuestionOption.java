package com.project.textbookres.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class QuestionOption {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    private String description;

    private boolean correct;
}
