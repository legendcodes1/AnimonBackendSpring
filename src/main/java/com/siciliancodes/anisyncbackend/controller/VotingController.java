package com.siciliancodes.anisyncbackend.controller;

import com.siciliancodes.anisyncbackend.dto.request.CastVoteRequest;
import com.siciliancodes.anisyncbackend.dto.response.VoteCountResponse;
import com.siciliancodes.anisyncbackend.dto.response.VoteResponse;
import com.siciliancodes.anisyncbackend.entity.Vote;
import com.siciliancodes.anisyncbackend.service.VoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/votes")
@RequiredArgsConstructor
public class VotingController {

    private final VoteService voteService;

    // Cast a vote
    @PostMapping
    public ResponseEntity<VoteResponse> castVote(@Valid @RequestBody CastVoteRequest request) {
        Vote vote = voteService.castVote(
                request.getNominationId(),
                request.getUserId(),
                request.getVoteType()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(mapToResponse(vote));
    }

    // Remove a vote
    @DeleteMapping("/{nominationId}/users/{userId}")
    public ResponseEntity<Void> removeVote(
            @PathVariable UUID nominationId,
            @PathVariable UUID userId) {
        voteService.removeVote(nominationId, userId);
        return ResponseEntity.noContent().build();
    }

    // Get all votes for a nomination
    @GetMapping("/nominations/{nominationId}")
    public ResponseEntity<List<VoteResponse>> getVotesForNomination(@PathVariable UUID nominationId) {
        List<Vote> votes = voteService.getVotesForNomination(nominationId);
        List<VoteResponse> responses = votes.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    // Get vote counts for a nomination
    @GetMapping("/nominations/{nominationId}/counts")
    public ResponseEntity<VoteCountResponse> getVoteCounts(@PathVariable UUID nominationId) {
        long upvotes = voteService.getUpvoteCount(nominationId);
        long downvotes = voteService.getDownvoteCount(nominationId);
        long vetoes = voteService.getVetoCount(nominationId);

        VoteCountResponse response = VoteCountResponse.builder()
                .nominationId(nominationId)
                .upvotes(upvotes)
                .downvotes(downvotes)
                .vetoes(vetoes)
                .build();

        return ResponseEntity.ok(response);
    }

    // Helper method: Entity → DTO
    private VoteResponse mapToResponse(Vote vote) {
        return VoteResponse.builder()
                .nominationId(vote.getNomination().getId())
                .userId(vote.getUser().getId())
                .voteType(vote.getVoteType())
                .animeTitle(vote.getNomination().getAnimeTitle())
                .createdAt(vote.getCreatedAt())
                .build();
    }
}