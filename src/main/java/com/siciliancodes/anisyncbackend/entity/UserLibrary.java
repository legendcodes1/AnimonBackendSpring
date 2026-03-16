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
@Table(name = "user_library")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserLibrary {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "anime_id", nullable = false)
    private String animeId;

    @Column(name = "anime_title", nullable = false)
    private String animeTitle;

    @Column(name = "type", nullable = false)
    private String type; // "anime" or "manga"

    @Column(name = "status", nullable = false)
    private String status; // "watching", "completed", etc.

    @Column(name = "anime_poster")
    private String animePoster;

    @Column(name = "rating")
    private Double rating; // Changed from Integer to Double

    @Column(name = "episodes_watched")
    private Integer episodesWatched;

    @Column(name = "total_episodes")
    private Integer totalEpisodes;

    @Column(name = "chapters_read")
    private Integer chaptersRead;

    @Column(name = "total_chapters")
    private Integer totalChapters;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @CreationTimestamp
    @Column(name = "added_at")
    private LocalDateTime addedAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}