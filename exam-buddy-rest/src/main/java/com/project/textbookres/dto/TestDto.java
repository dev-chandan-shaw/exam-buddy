package com.project.textbookres.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestDto {
    private Long id;
    private String title;
    private int totalQuestions;
    private int totalMarks;
    private int totalTime;
    private LocalDateTime lastAttemptedAt;
    @JsonProperty("isPyqTest")
    private boolean pyqTest;
    @JsonProperty("isSectionTest")
    private boolean sectionTest;
    @JsonProperty("isAttempted")
    private boolean attempted;
    @JsonProperty("isPaused")
    private boolean paused;
}
