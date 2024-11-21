package com.github.nahualvisionsback.controller;

import com.github.nahualvisionsback.dto.UserProfile;
import com.github.nahualvisionsback.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping ("/api/user")
@RequiredArgsConstructor
public class UserController {
    final UserService userService;

    @GetMapping("/")
    public ResponseEntity<UserProfile> getUserByUUID(@RequestHeader("Authorization") String authorizationHeader) {
        if(authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            UserProfile user = userService.getUserProfile(authorizationHeader.substring(7));
            return ResponseEntity.ok(user);
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
    }

    @GetMapping("/anton")
    public String getAnton(){
        return "Anton !!!";
    }
}
