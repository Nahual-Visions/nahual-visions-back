package com.github.nahualvisionsback.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class JwtRequest {
    private String login;
    private String password;
}
