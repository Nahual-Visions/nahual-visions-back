package com.github.nahualvisionsback.repository;

import com.github.nahualvisionsback.entity.HistoryEntity;
import com.github.nahualvisionsback.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface HistoryRepository extends JpaRepository<HistoryEntity, Integer> {
    List<HistoryEntity> findAllByUser(UserEntity user, Pageable pageable);
    HistoryEntity findById(UUID id);
}
