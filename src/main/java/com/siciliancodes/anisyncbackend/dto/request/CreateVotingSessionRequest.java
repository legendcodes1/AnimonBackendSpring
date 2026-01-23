package com.siciliancodes.anisyncbackend.dto.request;


import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class CreateVotingSessionRequest {
    @NotNull(message = "Creator ID is required")
    private UUID createdBy;

    @NotNull(message = "End time is required")
    private LocalDateTime endTime;
}