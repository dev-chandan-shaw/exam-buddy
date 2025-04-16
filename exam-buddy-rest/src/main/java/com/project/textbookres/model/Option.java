//package com.project.textbookres.model;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonProperty;
//import jakarta.persistence.*;
//
//@Entity
//public class Option {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    @ManyToOne
//    @JoinColumn(name = "question_id")
//    @JsonIgnore
//    private Question question;
//    private String text;
//    @JsonIgnore
//    private boolean correct = false;
//
//
//}
