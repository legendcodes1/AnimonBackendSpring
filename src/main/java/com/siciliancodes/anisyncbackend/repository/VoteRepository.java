package com.siciliancodes.anisyncbackend.repository;

import com.siciliancodes.anisyncbackend.entity.Vote;
import com.siciliancodes.anisyncbackend.entity.VoteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface VoteRepository extends JpaRepository<Vote, VoteId> {  // ✅ Vote entity, VoteId key

    // ✅ Find all votes by a user
    List<Vote> findByUserId(UUID userId);

    // ✅ Find all votes for a nomination
    List<Vote> findByNominationId(UUID nominationId);

    // ✅ Count votes by type for a nomination
    long countByNominationIdAndVoteType(UUID nominationId, String voteType);

    // ✅ Check if user already voted on nomination
    boolean existsByNominationIdAndUserId(UUID nominationId, UUID userId);

    // ✅ Count user's vetoes (for veto limit check)
    long countByUserIdAndVoteType(UUID userId, String voteType);

    // ✅ Find all votes in a voting session (through nomination)
    // This would need a custom query:
    @Query("SELECT v FROM Vote v WHERE v.nomination.votingSession.id = :sessionId")
    List<Vote> findByVotingSessionId(@Param("sessionId") UUID sessionId);
}