package com.project.textbookres.dto;

import lombok.Getter;

@Getter
public class CreateTopicRequest {
    private String name;
    private long subjectId;
}
