package com.project.textbookres.dto;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CreateExamRequest {
    private String title;
    private int marksPerQuestion;
    private double negativeMark;
    private final List<CreateExamSection> sections = new ArrayList<>();
}

