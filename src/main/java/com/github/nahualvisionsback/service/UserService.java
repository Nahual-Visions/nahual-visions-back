package com.github.nahualvisionsback.service;

import com.github.nahualvisionsback.entity.UserEntity;
import com.github.nahualvisionsback.repository.UserRepository;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

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

}
