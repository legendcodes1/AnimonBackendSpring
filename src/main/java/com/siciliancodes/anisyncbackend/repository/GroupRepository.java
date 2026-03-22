package com.siciliancodes.anisyncbackend.repository;

import com.siciliancodes.anisyncbackend.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GroupRepository extends JpaRepository<Group, UUID> {


    Optional<Group> findByName(String name);


    boolean existsByName(String name);


    List<Group> findByCreatedById(UUID createdById);


    List<Group> findByNameContainingIgnoreCase(String keyword);

    long countByCreatedById(UUID createdById);
}