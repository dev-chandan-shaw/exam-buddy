package com.project.textbookres.dto;

import com.project.textbookres.model.Subject;
import lombok.Data;

import java.util.List;

@Data
public class ActiveTestSectionResponse {
    private long id;
    private Subject subject;
    private int timeTakenSeconds;
    private List<ActiveTestQuestionStateResponse> questions;
}
