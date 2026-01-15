package com.siciliancodes.anisyncbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "nominations")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Nomination {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // ✅ THIS IS THE KEY: Link to voting session
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "voting_session_id", nullable = false)
    private VotingSession votingSession;

    // Anime/Manga details (from external API)
    @Column(name = "anime_id", nullable = false, length = 50)
    private String animeId;

    @Column(name = "anime_title", nullable = false)
    private String animeTitle;

    @Column(name = "anime_poster")  // ✅ Fixed: was anime_picture
    private String animePoster;

    @Column(nullable = false)
    private String type;  // "anime" or "manga"

    // Who nominated this?
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "nominated_by", nullable = false)  // ✅ Fixed: was using @MapsId
    private User nominatedBy;

    // AI-calculated match score (0-100)
    @Column(name = "match_score")
    private Integer matchScore;

    // All votes for THIS nomination
    @OneToMany(mappedBy = "nomination", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @Builder.Default
    private Set<Vote> votes = new HashSet<>();

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // Helper: Get vote counts
    public long getUpvoteCount() {
        return votes.stream().filter(v -> "upvote".equals(v.getVoteType())).count();
    }

    public long getDownvoteCount() {
        return votes.stream().filter(v -> "downvote".equals(v.getVoteType())).count();
    }

    public long getVetoCount() {
        return votes.stream().filter(v -> "veto".equals(v.getVoteType())).count();
    }
}