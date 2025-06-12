package com.project.textbookres.dto;

import lombok.Data;
import lombok.Getter;

@Data
public class SignInRequest {
    private String email;
    private String password;
}
