package com.github.nahualvisionsback.repository;

import com.github.nahualvisionsback.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    UserEntity findByEmail(String email);
    UserEntity findByUsername(String username);
    UserEntity findById(UUID id);
    UserEntity findByUsernameAndEmail(String username, String email);
}
