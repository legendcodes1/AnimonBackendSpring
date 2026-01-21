package com.siciliancodes.anisyncbackend.service;

import com.siciliancodes.anisyncbackend.entity.Group;
import com.siciliancodes.anisyncbackend.entity.User;
import com.siciliancodes.anisyncbackend.entity.VotingSession;
import com.siciliancodes.anisyncbackend.repository.GroupRepository;
import com.siciliancodes.anisyncbackend.repository.UserRepository;
import com.siciliancodes.anisyncbackend.repository.VotingSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VotingSessionService {

    private final VotingSessionRepository votingSessionRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    @Transactional
    public VotingSession createVotingSession(UUID groupId, UUID createdById, LocalDateTime endTime) {
        // 1. Get group
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found: " + groupId));

        // 2. Get creator
        User creator = userRepository.findById(createdById)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + createdById));

        // 3. Check if there's already an active session
        List<VotingSession> activeSessions = votingSessionRepository
                .findByGroupIdAndStatus(groupId, "active");

        if (!activeSessions.isEmpty()) {
            throw new IllegalArgumentException("Group already has an active voting session");
        }

        // 4. Validate end time
        if (endTime.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("End time must be in the future");
        }

        // 5. Create session
        VotingSession session = VotingSession.builder()
                .group(group)
                .createdBy(creator)
                .status("active")
                .endTime(endTime)
                .build();

        return votingSessionRepository.save(session);
    }

    @Transactional
    public void endVotingSession(UUID sessionId) {
        // 1. Get session
        VotingSession session = votingSessionRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("Voting session not found: " + sessionId));

        // 2. Check if already ended
        if (session.getStatus().equals("ended")) {
            throw new IllegalArgumentException("Voting session already ended");
        }

        // 3. End session
        session.setStatus("ended");
        votingSessionRepository.save(session);
    }

    public VotingSession getSessionById(UUID sessionId) {
        return votingSessionRepository.findById(sessionId)
                .orElseThrow(() -> new IllegalArgumentException("Voting session not found: " + sessionId));
    }

    public List<VotingSession> getGroupSessions(UUID groupId) {
        return votingSessionRepository.findByGroupId(groupId);
    }

    public List<VotingSession> getActiveSessions(UUID groupId) {
        return votingSessionRepository.findByGroupIdAndStatus(groupId, "active");
    }

    public VotingSession getActiveSession(UUID groupId) {
        return votingSessionRepository
                .findFirstByGroupIdAndStatusOrderByCreatedAtDesc(groupId, "active")
                .orElseThrow(() -> new IllegalArgumentException("No active voting session for this group"));
    }

    @Transactional
    public void autoEndExpiredSessions() {
        // This method can be called by a scheduled job
        List<VotingSession> activeSessions = votingSessionRepository.findByStatus("active");

        for (VotingSession session : activeSessions) {
            if (!session.isActive()) {  // Uses helper method in entity
                session.setStatus("ended");
                votingSessionRepository.save(session);
            }
        }
    }
}