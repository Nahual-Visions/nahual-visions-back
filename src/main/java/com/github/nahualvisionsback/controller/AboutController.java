package com.github.nahualvisionsback.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AboutController {
    @GetMapping
    public String isOnline() {
        return "OK";
    }
}
