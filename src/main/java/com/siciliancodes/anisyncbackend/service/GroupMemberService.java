package com.siciliancodes.anisyncbackend.service;

import com.siciliancodes.anisyncbackend.entity.Group;
import com.siciliancodes.anisyncbackend.entity.GroupMember;
import com.siciliancodes.anisyncbackend.entity.GroupMemberId;
import com.siciliancodes.anisyncbackend.entity.User;
import com.siciliancodes.anisyncbackend.repository.GroupMemberRepository;
import com.siciliancodes.anisyncbackend.repository.GroupRepository;
import com.siciliancodes.anisyncbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupMemberService {

    private final GroupMemberRepository groupMemberRepository;
    private final GroupRepository groupRepository;
    private final UserRepository userRepository;

    @Transactional
    public GroupMember joinGroup(UUID groupId, UUID userId) {
        // 1. Get group
        Group group = groupRepository.findById(groupId)
                .orElseThrow(() -> new IllegalArgumentException("Group not found: " + groupId));

        // 2. Get user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        // 3. Check if already a member
        if (groupMemberRepository.existsByGroupIdAndUserId(groupId, userId)) {
            throw new IllegalArgumentException("User is already a member of this group");
        }

        // 4. Check group size limit (business rule)
        long memberCount = groupMemberRepository.countByGroupId(groupId);
        if (memberCount >= 50) {
            throw new IllegalArgumentException("Group is full (max 50 members)");
        }

        // 5. Create membership
        GroupMemberId memberId = new GroupMemberId(groupId, userId);
        GroupMember groupMember = GroupMember.builder()
                .id(memberId)
                .group(group)
                .user(user)
                .role("member")  // Default role
                .build();

        return groupMemberRepository.save(groupMember);
    }

    @Transactional
    public void leaveGroup(UUID groupId, UUID userId) {
        // Check if member exists
        if (!groupMemberRepository.existsByGroupIdAndUserId(groupId, userId)) {
            throw new IllegalArgumentException("User is not a member of this group");
        }

        // Delete membership
        groupMemberRepository.deleteByGroupIdAndUserId(groupId, userId);
    }

    public List<GroupMember> getGroupMembers(UUID groupId) {
        return groupMemberRepository.findByGroupId(groupId);
    }

    public List<GroupMember> getUserGroups(UUID userId) {
        return groupMemberRepository.findByUserId(userId);
    }

    public long getGroupMemberCount(UUID groupId) {
        return groupMemberRepository.countByGroupId(groupId);
    }

    public boolean isMember(UUID groupId, UUID userId) {
        return groupMemberRepository.existsByGroupIdAndUserId(groupId, userId);
    }

    @Transactional
    public GroupMember updateRole(UUID groupId, UUID userId, String newRole) {
        // Get membership
        GroupMemberId memberId = new GroupMemberId(groupId, userId);
        GroupMember member = groupMemberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found"));

        // Validate role
        if (!newRole.equals("admin") && !newRole.equals("moderator") && !newRole.equals("member")) {
            throw new IllegalArgumentException("Invalid role. Must be: admin, moderator, or member");
        }

        // Update role
        member.setRole(newRole);
        return groupMemberRepository.save(member);
    }
}