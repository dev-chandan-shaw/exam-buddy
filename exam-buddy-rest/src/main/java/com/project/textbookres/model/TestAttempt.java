package com.project.textbookres.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class TestAttempt {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @NotNull
    @JsonIgnore
    private Test test;

    @ManyToOne
    @NotNull
    @JsonIgnore
    private User user;

    @NotNull
    private LocalDateTime startTime = LocalDateTime.now();

    @NotNull
    private LocalDateTime lastResumedAt = LocalDateTime.now();


    private boolean paused;

    private boolean completed;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TestAttemptSection> testSections = new ArrayList<>();
}
