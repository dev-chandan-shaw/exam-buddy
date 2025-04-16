package com.project.textbookres.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
public class TestSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private int marks;

    private int time;

    @ManyToOne
    private Subject subject;

    @ManyToMany
    private List<Question> questions = new ArrayList<>();
}
