package com.siciliancodes.anisyncbackend.controller;


import com.siciliancodes.anisyncbackend.dto.request.UnlockAchievementRequest;
import com.siciliancodes.anisyncbackend.dto.response.AchievementResponse;
import com.siciliancodes.anisyncbackend.entity.UserAchievements;
import com.siciliancodes.anisyncbackend.service.AchievementService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/achievements")
@RequiredArgsConstructor
public class AchievementController {

    private final AchievementService achievementService;

    // Unlock achievement
    @PostMapping("/unlock")
    public ResponseEntity<AchievementResponse> unlockAchievement(
            @Valid @RequestBody UnlockAchievementRequest request) {

        UserAchievements achievement = achievementService.unlockAchievement(
                request.getUserId(),
                request.getAchievementId()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(mapToResponse(achievement));
    }

    // Get user's achievements
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<AchievementResponse>> getUserAchievements(@PathVariable UUID userId) {
        List<UserAchievements> achievements = achievementService.getUserAchievements(userId);

        return ResponseEntity.ok(
                achievements.stream()
                        .map(this::mapToResponse)
                        .collect(Collectors.toList())
        );
    }

    // Helper
    private AchievementResponse mapToResponse(UserAchievements achievement) {
        return AchievementResponse.builder()
                .id(achievement.getId())
                .userId(achievement.getUser().getId())
                .achievementId(achievement.getAchievementId())
                .unlockedAt(achievement.getUnlockedAt())
                .build();
    }
}