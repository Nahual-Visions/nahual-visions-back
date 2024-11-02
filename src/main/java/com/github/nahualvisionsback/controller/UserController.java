package com.github.nahualvisionsback.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/anton")
public class UserController {
    @GetMapping()
    public String getAnton(){
        return "Anton gey!!!";
    }
}
