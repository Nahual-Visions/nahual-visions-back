package com.github.nahualvisionsback.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@AllArgsConstructor
public class RegistrationRequest {
    private String username;
    private String email;
    private String password;
}
