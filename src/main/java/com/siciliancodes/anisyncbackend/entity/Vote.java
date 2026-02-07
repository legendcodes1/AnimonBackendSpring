package com.siciliancodes.anisyncbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "votes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vote {

    @EmbeddedId  // ✅ Composite primary key
    private VoteId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("nominationId")  // ✅ Maps to nominationId in composite key
    @JoinColumn(name = "nomination_id")
    private Nomination nomination;  // ✅ What they're voting on

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")  // ✅ Maps to userId in composite key
    @JoinColumn(name = "user_id")
    private User user;  // ✅ Who is voting

    @Column(name = "vote_type", length = 10, nullable = false)
    private String voteType;  // ✅ 'upvote', 'downvote', 'veto'

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;
}