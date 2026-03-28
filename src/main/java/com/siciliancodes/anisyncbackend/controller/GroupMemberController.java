package com.siciliancodes.anisyncbackend.controller;


import com.siciliancodes.anisyncbackend.dto.GroupMemberDTO;
import com.siciliancodes.anisyncbackend.service.GroupMemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/group-members")
@RequiredArgsConstructor
public class GroupMemberController {


    private final GroupMemberService groupMemberService;

    @GetMapping("/{groupId}/members")
    public ResponseEntity<List<GroupMemberDTO>> getAllGroupMembers(@PathVariable UUID groupId) {
        return ResponseEntity.ok(groupMemberService.getGroupMembers(groupId));
    }

    @PutMapping("/{groupId}/members/{userId}")
    public ResponseEntity<GroupMemberDTO> updateGroup(@PathVariable UUID groupId, @PathVariable UUID userId, @RequestBody String newRole) {

        return ResponseEntity.ok(groupMemberService.updateRole(groupId, userId, newRole));
    }

    @PostMapping("/{groupId}/members/{userId}")
    public ResponseEntity<?> joinGroup(@PathVariable UUID groupId, @PathVariable UUID userId){
        try {
            GroupMemberDTO dto = groupMemberService.joinGroup(groupId, userId);
            return ResponseEntity.status(201).body(dto);  // Joined successfully
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(409).body(Map.of("message", e.getMessage()));
        } catch (RuntimeException e) {
            return ResponseEntity.status(400).body(Map.of("message", e.getMessage()));
        }
    }

    @DeleteMapping("/{groupId}/leave/{userId}")
    public ResponseEntity<Void> leaveGroup(@PathVariable UUID groupId, @PathVariable UUID userId){
        groupMemberService.leaveGroup(groupId, userId);
        return ResponseEntity.noContent().build();
    }
}
