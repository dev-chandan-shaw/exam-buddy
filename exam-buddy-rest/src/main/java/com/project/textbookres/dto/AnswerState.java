package com.project.textbookres.dto;

import com.fasterxml.jackson.annotation.JsonValue;

public enum AnswerState {
    NOT_VISITED,
    ANSWERED,
    MARKED,
    NOT_ANSWERED,
    MARKED_AND_ANSWERED;

    @JsonValue
    public int toValue() {
        return this.ordinal();
    }
}
