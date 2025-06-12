package com.project.textbookres.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class SavedQuestion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Test test;

    @ManyToOne
    private Question question;

    @ManyToOne
    private User user;
}

