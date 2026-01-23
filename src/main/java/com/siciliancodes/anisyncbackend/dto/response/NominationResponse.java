package com.siciliancodes.anisyncbackend.dto.response;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class NominationResponse {
    private UUID id;
    private UUID votingSessionId;
    private String animeId;
    private String animeTitle;
    private String animePoster;
    private String type;
    private UUID nominatedById;
    private String nominatedByUsername;
    private Integer matchScore;
    private long upvoteCount;
    private long downvoteCount;
    private long vetoCount;
    private LocalDateTime createdAt;
}