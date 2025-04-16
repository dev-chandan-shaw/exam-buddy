package com.project.textbookres.dto;

import lombok.Getter;

@Getter
public class CreateTestRequest {
    private String title;
    private boolean pyqTest;
    private long examId;
}
