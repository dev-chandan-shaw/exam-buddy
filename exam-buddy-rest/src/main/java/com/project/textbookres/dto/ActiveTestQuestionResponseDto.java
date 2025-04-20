package com.project.textbookres.dto;

import lombok.Data;

import java.util.List;

@Data
public class ActiveTestQuestionResponseDto {
    private long id;
    private String description;
    private String passage;
    private List<OptionResponseDto> options;
}
