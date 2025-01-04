package com.github.nahualvisionsback.controller;

import com.github.nahualvisionsback.dto.HistoryResponse;
import com.github.nahualvisionsback.dto.HistorySuggest;
import com.github.nahualvisionsback.dto.UserProfile;
import com.github.nahualvisionsback.entity.HistoryEntity;
import com.github.nahualvisionsback.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/history")
@RequiredArgsConstructor
public class HistoryController {
    private final HistoryService historyService;

    @GetMapping("/{page_number}")
    public ResponseEntity<List<HistoryResponse>> getHistoryPage(@PathVariable int page_number,
                                                                @RequestHeader("Authorization") String authorizationHeader) {
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            if (page_number <= 0) return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            page_number--;
            var historyList = historyService.getHistoryPage(
                                authorizationHeader.substring(7), page_number).orElse(null);
            if(historyList != null) {
                List<HistoryResponse> respList = historyList.stream()
                        .map(elem -> new HistoryResponse(elem.getId().toString(),
                                elem.getResponce(),
                                elem.getTimestamp().toString(),
                                elem.getSuccess()))
                        .toList();
                return new ResponseEntity<>(respList, HttpStatus.OK);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }


    @PostMapping("/suggest")
    public ResponseEntity<HistoryResponse> history(@RequestBody HistorySuggest suggest){
        HistoryEntity entity = historyService.updateHistoryEntity(suggest).orElse(null);
        if(entity != null) {
            return ResponseEntity.ok(new HistoryResponse(
                    entity.getId().toString(),
                    entity.getResponce(),
                    entity.getTimestamp().toString(),
                    entity.getSuccess()
            ));
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
