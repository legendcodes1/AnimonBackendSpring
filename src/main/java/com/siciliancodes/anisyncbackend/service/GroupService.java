package com.siciliancodes.anisyncbackend.service;

import com.siciliancodes.anisyncbackend.entity.Group;
import com.siciliancodes.anisyncbackend.entity.User;
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
public class GroupService {

    private final GroupRepository groupRepository;
    private final UserRepository userRepository;  //  Need this to get User

    @Transactional
    public Group createGroup(String name, String description, String avatarUrl, UUID createdByUserId) {
        if (groupRepository.existsByName(name)) {
            throw new IllegalArgumentException("Group name already exists: " + name);
        }


        User createdBy = userRepository.findById(createdByUserId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + createdByUserId));

        Group group = Group.builder()
                .name(name)
                .description(description)
                .avatarUrl(avatarUrl)
                .createdBy(createdBy)
                .build();

        return groupRepository.save(group);
    }

    public Group getGroupById(UUID id) {
        return groupRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Group not found with id: " + id));
    }

    public Group getGroupByName(String name) {
        return groupRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Group not found: " + name));
    }

    public List<Group> getAllGroups() {
        return groupRepository.findAll();
    }

    public List<Group> getGroupsCreatedByUser(UUID userId) {
        return groupRepository.findByCreatedById(userId);
    }

    @Transactional
    public Group updateGroup(UUID groupId, String name, String description, String avatarUrl) {
        Group group = getGroupById(groupId);

        // Update only non-null fields
        if (name != null && !name.equals(group.getName())) {
            // Check if new name already exists
            if (groupRepository.existsByName(name)) {
                throw new IllegalArgumentException("Group name already exists: " + name);
            }
            group.setName(name);
        }

        if (description != null) {
            group.setDescription(description);
        }

        if (avatarUrl != null) {
            group.setAvatarUrl(avatarUrl);
        }

        return groupRepository.save(group);
    }

    @Transactional
    public void deleteGroup(UUID groupId) {
        if (!groupRepository.existsById(groupId)) {
            throw new IllegalArgumentException("Group not found with id: " + groupId);
        }
        groupRepository.deleteById(groupId);
    }
}