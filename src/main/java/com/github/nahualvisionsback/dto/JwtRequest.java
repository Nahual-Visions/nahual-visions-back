package com.github.nahualvisionsback.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtRequest {
    private String username;
    private String email;
    private String password;
}
