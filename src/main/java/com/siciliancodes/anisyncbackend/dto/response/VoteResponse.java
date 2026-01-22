package com.siciliancodes.anisyncbackend.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class VoteResponse {
    private UUID nominationId;
    private UUID userId;
    private String voteType;
    private String animeTitle;  // From nomination
    private LocalDateTime createdAt;
}