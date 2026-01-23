package com.siciliancodes.anisyncbackend.controller;


import com.siciliancodes.anisyncbackend.dto.request.CreateGroupRequest;
import com.siciliancodes.anisyncbackend.dto.request.UpdateGroupRequest;
import com.siciliancodes.anisyncbackend.dto.response.GroupResponse;
import com.siciliancodes.anisyncbackend.entity.Group;
import com.siciliancodes.anisyncbackend.service.GroupService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    // Create group
    @PostMapping
    public ResponseEntity<GroupResponse> createGroup(@Valid @RequestBody CreateGroupRequest request) {
        Group group = groupService.createGroup(
                request.getName(),
                request.getDescription(),
                request.getAvatarUrl(),
                request.getCreatedBy()  // User ID from request
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(mapToResponse(group));
    }

    // Get group by ID
    @GetMapping("/{id}")
    public ResponseEntity<GroupResponse> getGroupById(@PathVariable UUID id) {
        Group group = groupService.getGroupById(id);
        return ResponseEntity.ok(mapToResponse(group));
    }

    // Get all groups
    @GetMapping
    public ResponseEntity<List<GroupResponse>> getAllGroups() {
        List<Group> groups = groupService.getAllGroups();
        return ResponseEntity.ok(groups.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList()));
    }

    // Get groups created by user
    @GetMapping("/creator/{userId}")
    public ResponseEntity<List<GroupResponse>> getGroupsCreatedByUser(@PathVariable UUID userId) {
        List<Group> groups = groupService.getGroupsCreatedByUser(userId);
        return ResponseEntity.ok(groups.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList()));
    }

    // Update group
    @PutMapping("/{id}")
    public ResponseEntity<GroupResponse> updateGroup(
            @PathVariable UUID id,
            @Valid @RequestBody UpdateGroupRequest request) {

        Group group = groupService.updateGroup(
                id,
                request.getName(),
                request.getDescription(),
                request.getAvatarUrl()
        );
        return ResponseEntity.ok(mapToResponse(group));
    }

    // Delete group
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGroup(@PathVariable UUID id) {
        groupService.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }

    // Helper
    private GroupResponse mapToResponse(Group group) {
        return GroupResponse.builder()
                .id(group.getId())
                .name(group.getName())
                .description(group.getDescription())
                .avatarUrl(group.getAvatarUrl())
                .createdById(group.getCreatedBy().getId())
                .createdByUsername(group.getCreatedBy().getUsername())
                .memberCount(group.getMemberCount())
                .createdAt(group.getCreatedAt())
                .build();
    }
}