package com.siciliancodes.anisyncbackend.service;

import com.siciliancodes.anisyncbackend.entity.Nomination;
import com.siciliancodes.anisyncbackend.entity.User;
import com.siciliancodes.anisyncbackend.entity.VotingSession;
import com.siciliancodes.anisyncbackend.repository.NominationRepository;
import com.siciliancodes.anisyncbackend.repository.UserRepository;
import com.siciliancodes.anisyncbackend.repository.VotingSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NominationService {

    private final NominationRepository nominationRepository;
    private final VotingSessionRepository votingSessionRepository;
    private final UserRepository userRepository;

    @Transactional
    public Nomination nominateAnime(UUID votingSessionId, UUID userId,
                                    String animeId, String animeTitle,
                                    String animePoster, String type) {
        // 1. Get voting session
        VotingSession session = votingSessionRepository.findById(votingSessionId)
                .orElseThrow(() -> new IllegalArgumentException("Voting session not found"));

        // 2. Check if session is active
        if (!session.isActive()) {
            throw new IllegalArgumentException("Voting session has ended");
        }

        // 3. Get user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // 4. Check if anime already nominated
        if (nominationRepository.existsByVotingSessionIdAndAnimeId(votingSessionId, animeId)) {
            throw new IllegalArgumentException("This anime has already been nominated");
        }

        // 5. Check nomination limit per user (business rule)
        long userNominationCount = nominationRepository
                .findByVotingSessionId(votingSessionId)
                .stream()
                .filter(n -> n.getNominatedBy().getId().equals(userId))
                .count();

        if (userNominationCount >= 3) {
            throw new IllegalArgumentException("Maximum 3 nominations per user");
        }

        // 6. Create nomination
        Nomination nomination = Nomination.builder()
                .votingSession(session)
                .animeId(animeId)
                .animeTitle(animeTitle)
                .animePoster(animePoster)
                .type(type)  // "anime" or "manga"
                .nominatedBy(user)
                .matchScore(0)  // Will be calculated later
                .build();

        return nominationRepository.save(nomination);
    }

    public List<Nomination> getSessionNominations(UUID votingSessionId) {
        return nominationRepository.findByVotingSessionId(votingSessionId);
    }

    public Nomination getNominationById(UUID nominationId) {
        return nominationRepository.findById(nominationId)
                .orElseThrow(() -> new IllegalArgumentException("Nomination not found"));
    }

    @Transactional
    public void deleteNomination(UUID nominationId, UUID userId) {
        Nomination nomination = getNominationById(nominationId);

        // Only the person who nominated can delete
        if (!nomination.getNominatedBy().getId().equals(userId)) {
            throw new IllegalArgumentException("You can only delete your own nominations");
        }

        // Can't delete if voting already started (has votes)
        if (!nomination.getVotes().isEmpty()) {
            throw new IllegalArgumentException("Cannot delete nomination that already has votes");
        }

        nominationRepository.delete(nomination);
    }

    @Transactional
    public void updateMatchScore(UUID nominationId, Integer matchScore) {
        Nomination nomination = getNominationById(nominationId);
        nomination.setMatchScore(matchScore);
        nominationRepository.save(nomination);
    }
}