package com.project.textbookres.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ActiveTestResponse {
    private long id;
    private long testId;
    private LocalDateTime startTime;
    private boolean completed;
    private List<ActiveTestSectionResponse> testSections;
}

