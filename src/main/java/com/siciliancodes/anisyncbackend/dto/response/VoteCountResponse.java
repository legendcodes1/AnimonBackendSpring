package com.siciliancodes.anisyncbackend.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class VoteCountResponse {
    private UUID nominationId;
    private long upvotes;
    private long downvotes;
    private long vetoes;
}