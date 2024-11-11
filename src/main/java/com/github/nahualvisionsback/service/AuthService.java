package com.github.nahualvisionsback.service;

import com.github.nahualvisionsback.config.JwtProvider;
import com.github.nahualvisionsback.custexception.AuthException;
import com.github.nahualvisionsback.dto.JwtRequest;
import com.github.nahualvisionsback.dto.JwtResponse;
import com.github.nahualvisionsback.dto.RefreshJwtRequest;
import com.github.nahualvisionsback.dto.RegistrationRequest;
import com.github.nahualvisionsback.entity.UserEntity;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final Map<UUID, String> refreshTokenStorage = new HashMap<>();
    private final UserService userService;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    public JwtResponse login(@NotNull JwtRequest request) throws AuthException {
        final UserEntity user = userService.getByUser(request.getLogin())
                .orElseThrow(() -> new AuthException("User Not Found"));
        if (passwordEncoder.matches(request.getPassword(), user.getPasswordHash())) {
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateRefreshToken(user);
            refreshTokenStorage.put(user.getId(), refreshToken);
            return new JwtResponse(accessToken, refreshToken);
        }
        else throw new AuthException("Wrong Password");
    }

    public JwtResponse registration(@NotNull RegistrationRequest request) throws AuthException {
        UserEntity user = userService.getByUserAndEmail(request.getUsername(), request.getEmail())
                .orElse(null);
        if (user == null) {
            user = new UserEntity().toBuilder()
                    .id(UUID.randomUUID())
                    .username(request.getUsername())
                    .email(request.getEmail())
                    .passwordHash(passwordEncoder.encode(request.getPassword()))
                    .createdAt(Instant.now())
                    .updatedAt(Instant.now())
                    .build();
            userService.addUser(user);
            final String accessToken = jwtProvider.generateAccessToken(user);
            final String refreshToken = jwtProvider.generateAccessToken(user);
            refreshTokenStorage.put(user.getId(), refreshToken);
            return new JwtResponse(accessToken, refreshToken);
        }
        else throw new AuthException("User Already Exists");
    }

    public JwtResponse generateNewAccessToken(@NotNull String refreshToken) throws AuthException {
        if(jwtProvider.validateRefreshToken(refreshToken)) {
            final Claims claims = jwtProvider.getRefreshToken(refreshToken);
            final UUID userId = UUID.fromString(claims.getId());
            final String saveRefreshToken = refreshTokenStorage.get(userId);
            if (saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
                final UserEntity user = userService.getById(userId)
                        .orElseThrow(() -> new AuthException("User Not Found"));
                final String accessToken = jwtProvider.generateAccessToken(user);
                return new JwtResponse(accessToken, null);
            }
        }
        return new JwtResponse(null, null);
    }

    public JwtResponse generateNewRefreshToken(@NotNull String refreshToken) throws AuthException {
        if(jwtProvider.validateRefreshToken(refreshToken)) {
           final Claims claims = jwtProvider.getRefreshToken(refreshToken);
           final UUID userId = UUID.fromString(claims.getId());
           final String saveRefreshToken = refreshTokenStorage.get(userId);
           if(saveRefreshToken != null && saveRefreshToken.equals(refreshToken)) {
               final UserEntity user = userService.getById(userId)
                       .orElseThrow(() -> new AuthException("User Not Found"));
               final String accessToken = jwtProvider.generateAccessToken(user);
               final String newRefreshToken = jwtProvider.generateRefreshToken(user);
               refreshTokenStorage.put(user.getId(), newRefreshToken);
               return new JwtResponse(accessToken, newRefreshToken);
           }
        }
        throw new AuthException("Invalid Refresh Token");
    }

}
