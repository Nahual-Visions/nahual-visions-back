package com.github.nahualvisionsback.dto;

import com.github.nahualvisionsback.entity.UserEntity;
import groovyjarjarantlr4.v4.runtime.misc.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserProfile {
    String username;
    String email;
    String avatarUrl;
    String subscriptionExpiresAt;
    String subscriptionName;

    public UserProfile(@NotNull UserEntity user) {
        this.username = user.getUsername();
        this.email = user.getEmail();
        this.avatarUrl = user.getAvatarUrl();
        this.subscriptionExpiresAt = user.getSubscriptionExpiresAt() != null ? user.getSubscriptionExpiresAt().toString() : null;
        this.subscriptionName = "";
    }
}
