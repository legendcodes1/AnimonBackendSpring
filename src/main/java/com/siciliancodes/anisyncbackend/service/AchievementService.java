package com.siciliancodes.anisyncbackend.service;


import com.siciliancodes.anisyncbackend.entity.User;
import com.siciliancodes.anisyncbackend.entity.UserAchievements;
import com.siciliancodes.anisyncbackend.repository.UserAchievementRepository;
import com.siciliancodes.anisyncbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AchievementService {

    private final UserAchievementRepository achievementRepository;
    private final UserRepository userRepository;

    public UserAchievements unlockAchievement(UUID userId, String achievementId) {
        // Check if user exists
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Check if already unlocked
        if (achievementRepository.existsByUserIdAndAchievementId(userId, achievementId)) {
            throw new RuntimeException("Achievement already unlocked");
        }

        // Create and save achievement
        UserAchievements achievement = UserAchievements.builder()
                .user(user)
                .achievementId(achievementId)
                .build();

        return achievementRepository.save(achievement);
    }

    public List<UserAchievements> getUserAchievements(UUID userId) {
        return achievementRepository.findByUserId(userId);
    }
}