package com.siciliancodes.anisyncbackend.dto.response;


import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class VotingSessionResponse {
    private UUID id;
    private UUID groupId;
    private String groupName;
    private String status;
    private LocalDateTime endTime;
    private UUID createdById;
    private int nominationCount;
    private boolean isActive;
    private LocalDateTime createdAt;
}