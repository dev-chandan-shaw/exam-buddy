package com.project.textbookres.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class QuestionBank {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    private Exam exam;

}
