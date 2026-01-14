package com.siciliancodes.anisyncbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "user_library",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "anime_id"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLibrary {  // ✅ Better name

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)  // ✅ Relationship to User
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "anime_id", nullable = false, length = 50)
    private String animeId;  // ✅ From external API (AniList)

    @Column(name = "anime_title", nullable = false)
    private String animeTitle;

    @Column(name = "anime_poster")
    private String animePoster;  // ✅ URL to poster image

    @Column(length = 10)
    private String type;  // ✅ anime or manga

    @Column(length = 20)
    private String status;  // ✅ watching, completed, plan_to_watch, dropped

    @Column
    private Integer rating;  // ✅ 1-10 rating (nullable)

    @Column(name = "episodes_watched")
    private Integer episodesWatched = 0;  // ✅ Default 0

    @Column(name = "total_episodes")
    private Integer totalEpisodes;

    @Column(columnDefinition = "TEXT")
    private String notes;  // ✅ User's personal notes

    @CreationTimestamp
    @Column(name = "added_at", updatable = false)
    private LocalDateTime addedAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}