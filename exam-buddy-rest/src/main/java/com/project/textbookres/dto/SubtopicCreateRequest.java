package com.project.textbookres.dto;

import lombok.Data;
import lombok.Getter;

@Getter
public class SubtopicCreateRequest {
    private String name;
    private Long topicId;
}
