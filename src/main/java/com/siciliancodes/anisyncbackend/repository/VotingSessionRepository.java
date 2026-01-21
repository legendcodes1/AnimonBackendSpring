package com.siciliancodes.anisyncbackend.repository;

import com.siciliancodes.anisyncbackend.entity.VotingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface VotingSessionRepository extends JpaRepository<VotingSession, UUID> {

    // ✅ Find all sessions for a group
    List<VotingSession> findByGroupId(UUID groupId);

    // ✅ Find sessions by group and status (returns list)
    List<VotingSession> findByGroupIdAndStatus(UUID groupId, String status);

    // ✅ Find active session for a group (returns one)
    Optional<VotingSession> findFirstByGroupIdAndStatusOrderByCreatedAtDesc(UUID groupId, String status);

    // ✅ Find all active sessions
    List<VotingSession> findByStatus(String status);
}