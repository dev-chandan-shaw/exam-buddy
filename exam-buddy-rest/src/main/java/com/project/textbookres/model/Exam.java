package com.project.textbookres.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Exam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String title;

    private int marksPerQuestion;

    private double negativeMark;
//
//    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Test> tests = new ArrayList<>();

    @OneToMany(mappedBy = "exam", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Subject> subjects;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public int getMarksPerQuestion() {
        return marksPerQuestion;
    }

    public void setMarksPerQuestion(int marksPerQuestion) {
        this.marksPerQuestion = marksPerQuestion;
    }

    public double getNegativeMark() {
        return negativeMark;
    }

    public void setNegativeMark(double negativeMark) {
        this.negativeMark = negativeMark;
    }


    //    public int getTotalTime() {
//        return totalTime;
//    }
//
//    public void setTotalTime(int totalTime) {
//        this.totalTime = totalTime;
//    }
//
//    public int getTotalMarks() {
//        return totalMarks;
//    }
//
//    public void setTotalMarks(int totalMarks) {
//        this.totalMarks = totalMarks;
//    }

//    public List<Test> getTests() {
//        return tests;
//    }
//
//    public void setTests(List<Test> tests) {
//        this.tests = tests;
//    }
}
