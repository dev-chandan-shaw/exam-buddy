package com.project.textbookres.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class QuestionDTO {
    private String description;
    private String passage;
    private List<OptionDTO> options;
}
