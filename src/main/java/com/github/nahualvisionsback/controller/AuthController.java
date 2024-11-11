package com.github.nahualvisionsback.controller;

import com.github.nahualvisionsback.custexception.AuthException;
import com.github.nahualvisionsback.dto.JwtRequest;
import com.github.nahualvisionsback.dto.JwtResponse;
import com.github.nahualvisionsback.dto.RefreshJwtRequest;
import com.github.nahualvisionsback.dto.RegistrationRequest;
import com.github.nahualvisionsback.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("login")
    public ResponseEntity<JwtResponse> login (@RequestBody JwtRequest jwtRequest) throws AuthException {
        final JwtResponse token = authService.login(jwtRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping("reg")
    public ResponseEntity<JwtResponse> registration (@RequestBody RegistrationRequest regRequest) throws AuthException {
        final JwtResponse token = authService.registration(regRequest);
        return ResponseEntity.ok(token);
    }

    @PostMapping("token")
    public ResponseEntity<JwtResponse> getNewAccessToken (@RequestBody RefreshJwtRequest refRequest) throws AuthException {
        final JwtResponse token = authService.generateNewAccessToken(refRequest.getRefreshToken());
        return ResponseEntity.ok(token);
    }

    @PostMapping("refresh")
    public ResponseEntity<JwtResponse> refreshToken (@RequestBody RefreshJwtRequest refRequest) throws AuthException {
        final JwtResponse token = authService.generateNewRefreshToken(refRequest.getRefreshToken());
        return ResponseEntity.ok(token);
    }

}
