package com.siciliancodes.anisyncbackend.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

// Composite Key Class
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class VoteId implements Serializable {
    @Column(name = "nomination_id")
    private UUID nominationId;

    @Column(name = "user_id")
    private UUID userId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoteId voteId = (VoteId) o;
        return Objects.equals(nominationId, voteId.nominationId) &&
                Objects.equals(userId, voteId.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nominationId, userId);
    }
}
