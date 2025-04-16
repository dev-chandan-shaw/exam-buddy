package com.project.textbookres.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class CreateQuestionRequest {
    private String description;
    private String passage;
    private List<OptionDTO> options;
    private long subtopicId;
    private long testSectionId;
    private long examId;
}
