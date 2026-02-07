package com.siciliancodes.anisyncbackend.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class GroupResponse {
    private UUID id;
    private String name;
    private String description;
    private String avatarUrl;
    private UUID createdById;
    private String createdByUsername;
    private Integer memberCount;
    private LocalDateTime createdAt;
}