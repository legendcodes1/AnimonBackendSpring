package com.siciliancodes.anisyncbackend.repository;

import com.siciliancodes.anisyncbackend.entity.UserAchievements;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserAchievementRepository extends JpaRepository<UserAchievements, UUID> {
    List<UserAchievements> findByUserId(UUID userId);
    Optional<UserAchievements> findByUserIdAndAchievementId(UUID userId, String achievementId);
    boolean existsByUserIdAndAchievementId(UUID userId, String achievementId);
}