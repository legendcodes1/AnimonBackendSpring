package com.siciliancodes.anisyncbackend.repository;

import com.siciliancodes.anisyncbackend.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<Group, UUID> {

    // ✅ FIXED: Capital N (matches field name in entity)
    Optional<Group> findByName(String name);

    // ✅ Check if group name exists (for validation)
    boolean existsByName(String name);

    // ✅ Find all groups created by a user
    List<Group> findByCreatedById(UUID createdById);

    // ✅ Find groups by name containing (search feature)
    List<Group> findByNameContainingIgnoreCase(String keyword);
}