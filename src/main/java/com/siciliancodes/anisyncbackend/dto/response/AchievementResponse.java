package com.siciliancodes.anisyncbackend.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class AchievementResponse {
    private UUID id;
    private UUID userId;
    private String achievementId;
    private LocalDateTime unlockedAt;
}