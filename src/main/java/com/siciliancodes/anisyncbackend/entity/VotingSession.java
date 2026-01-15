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
@Table(name = "voting_sessions")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VotingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // Which group is this voting session for?
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id", nullable = false)
    private Group group;

    // Who started this voting session?
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    // Is voting still open?
    @Column(nullable = false, length = 20)
    private String status;  // 'active' or 'ended'

    // When does voting close?
    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;

    // All nominations in THIS voting session
    @OneToMany(mappedBy = "votingSession", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude
    @Builder.Default
    private Set<Nomination> nominations = new HashSet<>();

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    // Helper method: Is voting still active?
    public boolean isActive() {
        return "active".equals(status) && LocalDateTime.now().isBefore(endTime);
    }

    // Helper method: Get nomination with most votes
    public Nomination getWinner() {
        return nominations.stream()
                .max((n1, n2) -> n1.getVotes().size() - n2.getVotes().size())
                .orElse(null);
    }
}