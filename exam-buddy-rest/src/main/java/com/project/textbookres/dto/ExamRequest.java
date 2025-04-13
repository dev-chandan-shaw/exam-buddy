package com.project.textbookres.dto;

import com.project.textbookres.model.Subject;

import java.util.ArrayList;
import java.util.List;

public class ExamRequest {
    private String title;

    private int marksPerQuestion;

    private double negativeMark;

    private List<Subject> subjects = new ArrayList<>();

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
}
