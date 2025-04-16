package com.project.textbookres.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Hello {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "greeting_id")
    private Greeting greeting;
}
