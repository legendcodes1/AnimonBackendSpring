package com.siciliancodes.anisyncbackend.repository;

import com.siciliancodes.anisyncbackend.entity.GroupMember;
import com.siciliancodes.anisyncbackend.entity.GroupMemberId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.UUID;


@Repository
public interface GroupMemberRepository extends JpaRepository<GroupMember, GroupMemberId> {
    List<GroupMember> findByGroupId(UUID groupId);
    List<GroupMember> findByUserId(UUID userId);
    boolean existsByGroupIdAndUserId(UUID groupId, UUID userId);
    long countByGroupId(UUID groupId);
    void deleteByGroupIdAndUserId(UUID groupId, UUID userId);
}