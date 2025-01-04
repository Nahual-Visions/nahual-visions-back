package com.github.nahualvisionsback.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class HistoryResponse {
    String id;
    String response;
    String timestamp;
    String success;
}
