package com.siciliancodes.anisyncbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_achievements")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserAchievement {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;  // ✅ Primary key

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "achievement_id", nullable = false, length = 50)
    private String achievementId;
    //references a predefined achievement (like "first_watch")

    @CreationTimestamp
    @Column(name = "unlocked_at")
    private LocalDateTime unlockedAt;

    // Add unique constraint so user can't unlock same achievement twice
    @Table(name = "user_achievements",
            uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "achievement_id"}))
}