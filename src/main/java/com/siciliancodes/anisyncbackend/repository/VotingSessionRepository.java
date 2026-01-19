package com.siciliancodes.anisyncbackend.repository;

import com.siciliancodes.anisyncbackend.entity.VotingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VotingSessionRepository extends JpaRepository<VotingSession, UUID> {
    List<VotingSession> findByGroupId(UUID groupId);
    List<VotingSession> findByGroupIdAndStatus(UUID groupId, String status);
    Optional<VotingSession> findByGroupIdAndStatus(UUID groupId, String status);
}