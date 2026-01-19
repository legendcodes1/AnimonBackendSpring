package com.siciliancodes.anisyncbackend.service;

import com.siciliancodes.anisyncbackend.entity.*;
import com.siciliancodes.anisyncbackend.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VoteService {

    private final VoteRepository voteRepository;
    private final NominationRepository nominationRepository;
    private final UserRepository userRepository;
    private final VotingSessionRepository votingSessionRepository;

    @Transactional
    public Vote castVote(UUID nominationId, UUID userId, String voteType) {
        // 1. Get nomination
        Nomination nomination = nominationRepository.findById(nominationId)
                .orElseThrow(() -> new IllegalArgumentException("Nomination not found"));

        // 2. Get user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 3. Check voting session is active
        VotingSession session = nomination.getVotingSession();
        if (!session.isActive()) {
            throw new IllegalArgumentException("Voting has ended");
        }

        // 4. Check if user already voted
        VoteId voteId = new VoteId(nominationId, userId);
        if (voteRepository.existsById(voteId)) {
            throw new IllegalArgumentException("You already voted on this nomination");
        }

        // 5. Validate vote type
        if (!voteType.equals("upvote") && !voteType.equals("downvote") && !voteType.equals("veto")) {
            throw new IllegalArgumentException("Invalid vote type. Must be: upvote, downvote, or veto");
        }

        // 6. Check veto limit (1 per session)
        if (voteType.equals("veto")) {
            long vetoCount = voteRepository.countByUserIdAndVoteType(userId, "veto");
            if (vetoCount >= 1) {
                throw new IllegalArgumentException("You've already used your veto");
            }
        }

        // 7. Create vote
        Vote vote = Vote.builder()
                .id(voteId)
                .nomination(nomination)
                .user(user)
                .voteType(voteType)
                .build();

        return voteRepository.save(vote);
    }

    @Transactional
    public void removeVote(UUID nominationId, UUID userId) {
        VoteId voteId = new VoteId(nominationId, userId);
        if (!voteRepository.existsById(voteId)) {
            throw new IllegalArgumentException("Vote not found");
        }
        voteRepository.deleteById(voteId);
    }

    public List<Vote> getVotesForNomination(UUID nominationId) {
        return voteRepository.findByNominationId(nominationId);
    }

    public long getUpvoteCount(UUID nominationId) {
        return voteRepository.countByNominationIdAndVoteType(nominationId, "upvote");
    }

    public long getDownvoteCount(UUID nominationId) {
        return voteRepository.countByNominationIdAndVoteType(nominationId, "downvote");
    }

    public long getVetoCount(UUID nominationId) {
        return voteRepository.countByNominationIdAndVoteType(nominationId, "veto");
    }
}