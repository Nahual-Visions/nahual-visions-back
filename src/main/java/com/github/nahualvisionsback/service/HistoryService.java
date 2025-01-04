package com.github.nahualvisionsback.service;

import com.github.nahualvisionsback.config.JwtProvider;
import com.github.nahualvisionsback.dto.HistorySuggest;
import com.github.nahualvisionsback.entity.HistoryEntity;
import com.github.nahualvisionsback.entity.UserEntity;
import com.github.nahualvisionsback.repository.HistoryRepository;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import io.jsonwebtoken.Claims;
import liquibase.logging.mdc.customobjects.History;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.spi.Limit;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class HistoryService {
    private final HistoryRepository historyRepository;
    private final UserService userService;
    private final JwtProvider jwtProvider;

    private UUID getIdFromToken(@NotNull String token) {
        final Claims claims = jwtProvider.getAccessClaims(token);
        return UUID.fromString(claims.getId());
    }

    public Optional<List<HistoryEntity>> getHistoryPage(@NonNull String token, int page_number) {
        Pageable pageable = PageRequest.of(page_number, 30);
        UUID userId = getIdFromToken(token);
        UserEntity user = userService.getById(userId).orElse(null);
        if(user != null) {
            return Optional.ofNullable(historyRepository.findAllByUser(user, pageable));
        }
        return Optional.empty();
    }

    public Optional<HistoryEntity> updateHistoryEntity(@NotNull HistorySuggest history) {
        HistoryEntity entity = historyRepository.findById(UUID.fromString(history.getHistoryId()));
        if(entity != null) {
            entity.setSuccess(history.getSuggest());
            historyRepository.save(entity);
            return Optional.of(entity);
        }
        return Optional.empty();
    }

    public String addHistory(@NonNull String token) {
        UUID userId = getIdFromToken(token);
        UserEntity user = userService.getById(userId).orElse(null);
        if (user != null) {
            historyRepository.save(new HistoryEntity(
                    UUID.randomUUID(),
                    user,
                    "Просто треш какой то",
                    Instant.now(),
                    "Успех")
            );
            return "Successfully";
        }
        else return "Error";
    }
}
