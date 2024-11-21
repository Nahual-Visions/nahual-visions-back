package com.github.nahualvisionsback.dto;

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
}
