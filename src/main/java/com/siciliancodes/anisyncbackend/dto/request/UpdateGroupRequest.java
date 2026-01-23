package com.siciliancodes.anisyncbackend.dto.request;

import lombok.Data;

@Data
public class UpdateGroupRequest {
    private String name;
    private String description;
    private String avatarUrl;
}