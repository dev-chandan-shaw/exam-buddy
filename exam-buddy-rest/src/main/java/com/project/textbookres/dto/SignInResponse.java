package com.project.textbookres.dto;

import lombok.Data;
import lombok.Setter;

@Data
public class SignInResponse {
    private String accessToken;
    private String firstName;
    private String lastName;
    private long id;
}
