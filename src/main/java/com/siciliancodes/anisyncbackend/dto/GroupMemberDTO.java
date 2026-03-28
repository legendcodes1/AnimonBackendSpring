package com.siciliancodes.anisyncbackend.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class GroupMemberDTO {
    private UUID groupId;
    private UUID userId;
    private String role;
}