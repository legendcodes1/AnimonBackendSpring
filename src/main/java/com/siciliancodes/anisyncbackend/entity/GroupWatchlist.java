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
@Table(name = "group_watchlist")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GroupWatchlist {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // ✅ FIXED: Should link to GROUP, not User
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)  // ✅ Changed from created_by
    private Group group;

    @Column(name = "anime_id", nullable = false, length = 50)
    private String animeId;  // ✅ Fixed: camelCase

    @Column(name = "anime_title", nullable = false)
    private String animeTitle;

    @Column(name = "anime_poster")  // ✅ Fixed: was anime_picture
    private String animePoster;

    @Column(length = 20)
    private String status = "watching";  // watching, completed, planned

    @Column(name = "current_episode")
    private Integer currentEpisode = 1;  // ✅ Fixed: camelCase, default 1 (not 0)

    @Column(name = "total_episodes")  // ✅ Fixed: episodeS (plural)
    private Integer totalEpisodes;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}