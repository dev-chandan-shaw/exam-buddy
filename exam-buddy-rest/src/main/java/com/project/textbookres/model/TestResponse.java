package com.project.textbookres.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class TestResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Test test;

    @ManyToOne
    private User user;

    @OneToMany(mappedBy = "testResponse", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AnswerResponse> answerResponses = new ArrayList<>();

    private Date startTime;

    private boolean testFinished;

    public TestResponse(Test test, User user, Date startTime) {
        this.test = test;
        this.user = user;
        this.startTime = startTime;
    }
}
