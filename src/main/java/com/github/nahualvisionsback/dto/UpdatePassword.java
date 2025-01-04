package com.github.nahualvisionsback.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class UpdatePassword {
    private String oldPassword;
    private String newPassword;
}
