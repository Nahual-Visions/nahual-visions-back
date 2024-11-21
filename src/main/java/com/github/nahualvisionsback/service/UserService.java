package com.github.nahualvisionsback.service;

import com.github.nahualvisionsback.config.JwtProvider;
import com.github.nahualvisionsback.dto.UserProfile;
import com.github.nahualvisionsback.entity.UserEntity;
import com.github.nahualvisionsback.repository.UserRepository;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;

    public Optional<UserEntity> getByUserAndEmail(@NotNull String username, @NotNull String email) {
        return Optional.ofNullable(userRepository.findByUsernameAndEmail(username, email));
    }

    public Optional<UserEntity> getByUser(@NotNull String username) {
        return Optional.ofNullable(userRepository.findByUsername(username));
    }

    public Optional<UserEntity> getByEmail(@NotNull String email) {
        return Optional.ofNullable(userRepository.findByEmail(email));
    }

    public Optional<UserEntity> getById(@NotNull UUID id) {
        return Optional.ofNullable(userRepository.findById(id));
    }

    public String addUser(@NotNull UserEntity user) {
        return userRepository.save(user).getId().toString();
    }

    public UserProfile getUserProfile(@NotNull String token) {
        final Claims claims = jwtProvider.getAccessClaims(token);
        final UUID userId = UUID.fromString(claims.getId());
        final UserEntity userEntity = userRepository.findById(userId);
        return new UserProfile(userEntity.getUsername(),
                userEntity.getEmail(),
                userEntity.getAvatarUrl(),
                userEntity.getSubscriptionExpiresAt() != null ? userEntity.getSubscriptionExpiresAt().toString() : "",
                userEntity.getSubscriptionId());
    }

}
