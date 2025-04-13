package com.project.textbookres.dto;

public class ApiResponse <T>{
    private String message;
    private T data;
    public ApiResponse(T data, String message) {
        this.message = message;
        this.data = data;
    }
}
