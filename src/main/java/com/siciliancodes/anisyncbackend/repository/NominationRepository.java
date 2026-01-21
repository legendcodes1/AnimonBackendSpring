package com.siciliancodes.anisyncbackend.repository;

import com.siciliancodes.anisyncbackend.entity.Nomination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NominationRepository extends JpaRepository<Nomination, UUID> {

    List<Nomination> findByVotingSessionId(UUID votingSessionId);

    boolean existsByVotingSessionIdAndAnimeId(UUID votingSessionId, String animeId);

    List<Nomination> findByNominatedById(UUID userId);

    long countByVotingSessionId(UUID votingSessionId);
}