package com.siciliancodes.anisyncbackend.controller;

import com.siciliancodes.anisyncbackend.dto.request.CreateNominationRequest;
import com.siciliancodes.anisyncbackend.dto.response.NominationResponse;
import com.siciliancodes.anisyncbackend.entity.Nomination;
import com.siciliancodes.anisyncbackend.service.NominationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/voting-sessions/{sessionId}/nominations")
@RequiredArgsConstructor
public class NominationController {

    private final NominationService nominationService;

    // Nominate anime
    @PostMapping
    public ResponseEntity<NominationResponse> nominateAnime(
            @PathVariable UUID sessionId,
            @Valid @RequestBody CreateNominationRequest request) {

        Nomination nomination = nominationService.nominateAnime(
                sessionId,
                request.getUserId(),
                request.getAnimeId(),
                request.getAnimeTitle(),
                request.getAnimePoster(),
                request.getType()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(mapToResponse(nomination));
    }

    // Get all nominations for session
    @GetMapping
    public ResponseEntity<List<NominationResponse>> getSessionNominations(@PathVariable UUID sessionId) {
        List<Nomination> nominations = nominationService.getSessionNominations(sessionId);
        return ResponseEntity.ok(nominations.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList()));
    }

    // Delete nomination
    @DeleteMapping("/{nominationId}")
    public ResponseEntity<Void> deleteNomination(
            @PathVariable UUID sessionId,
            @PathVariable UUID nominationId,
            @RequestParam UUID userId) {

        nominationService.deleteNomination(nominationId, userId);
        return ResponseEntity.noContent().build();
    }

    // Helper
    private NominationResponse mapToResponse(Nomination nomination) {
        return NominationResponse.builder()
                .id(nomination.getId())
                .votingSessionId(nomination.getVotingSession().getId())
                .animeId(nomination.getAnimeId())
                .animeTitle(nomination.getAnimeTitle())
                .animePoster(nomination.getAnimePoster())
                .type(nomination.getType())
                .nominatedById(nomination.getNominatedBy().getId())
                .nominatedByUsername(nomination.getNominatedBy().getUsername())
                .matchScore(nomination.getMatchScore())
                .upvoteCount(nomination.getUpvoteCount())
                .downvoteCount(nomination.getDownvoteCount())
                .vetoCount(nomination.getVetoCount())
                .createdAt(nomination.getCreatedAt())
                .build();
    }
}