package com.github.nahualvisionsback.service;

import com.github.nahualvisionsback.config.JwtProvider;
import com.github.nahualvisionsback.dto.UserProfile;
import com.github.nahualvisionsback.entity.UserEntity;
import com.github.nahualvisionsback.repository.UserRepository;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    private UUID getIdFromToken(@NotNull String token) {
        final Claims claims = jwtProvider.getAccessClaims(token);
        return UUID.fromString(claims.getId());
    }

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

    public Optional<UserProfile> updateUsername(@NotNull String token, @NotNull String newUsername) {
        final UUID userId = getIdFromToken(token);
        UserProfile userProfile = null;
        UserEntity user = userRepository.findById(userId);
        if(user != null) {
            user.setUsername(newUsername);
            userRepository.save(user);
            userProfile = new UserProfile(user);
        }
        return Optional.ofNullable(userProfile);
    }

    public Optional<UserProfile> updatePassword(@NotNull String token, @NotNull String password) {
        final UUID userId = getIdFromToken(token);
        UserProfile userProfile = null;
        UserEntity user = userRepository.findById(userId);
        if(user != null) {
            user.setPasswordHash(passwordEncoder.encode(password));
            userRepository.save(user);
            userProfile = new UserProfile(user);
        }
        return Optional.ofNullable(userProfile);
    }

    public Optional<UserProfile> updateAvatarUrl(@NotNull String token, @NotNull String avatarUrl) {
        final UUID userId = getIdFromToken(token);
        UserProfile userProfile = null;
        UserEntity user = userRepository.findById(userId);
        if(user != null) {
            user.setAvatarUrl(avatarUrl);
            userRepository.save(user);
            userProfile = new UserProfile(user);
        }
        return Optional.ofNullable(userProfile);
    }

    public String deleteUser(@NotNull String token) throws ResponseStatusException{
        final UUID userId = getIdFromToken(token);
        UserEntity user = userRepository.findById(userId);
        if(user != null) {
            userRepository.delete(user);
            return "Successfully deleted user";
        }
        return null;
    }

    public Optional<UserProfile> getUserProfile(@NotNull String token) {
        final UUID userId = getIdFromToken(token);
        final UserEntity userEntity = userRepository.findById(userId);
        if(userEntity != null) {
            return Optional.of(new UserProfile(userEntity));
        }
        return Optional.empty();
    }

}
