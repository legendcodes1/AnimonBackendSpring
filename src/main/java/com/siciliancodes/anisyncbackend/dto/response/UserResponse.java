package com.siciliancodes.anisyncbackend.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class UserResponse {
    private UUID id;
    private String username;
    private String email;
    private String avatarUrl;
    private Integer level;
    private Integer xp;
    private Integer currentStreak;
    private Integer longestStreak;
    private LocalDateTime createdAt;
}