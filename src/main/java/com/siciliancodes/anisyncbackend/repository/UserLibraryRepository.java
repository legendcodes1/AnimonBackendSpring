package com.siciliancodes.anisyncbackend.repository;

import com.siciliancodes.anisyncbackend.entity.UserLibrary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserLibraryRepository extends JpaRepository<UserLibrary, UUID> {  // ✅ Correct entity


    List<UserLibrary> findByUserId(UUID userId);


    List<UserLibrary> findByUserIdAndStatus(UUID userId, String status);


    List<UserLibrary> findByUserIdAndType(UUID userId, String type);


    Optional<UserLibrary> findByUserIdAndAnimeId(UUID userId, String animeId);


    boolean existsByUserIdAndAnimeId(UUID userId, String animeId);


    long countByUserIdAndStatus(UUID userId, String status);


    List<UserLibrary> findByUserIdAndTypeAndStatus(UUID userId, String type, String status);


    List<UserLibrary> findByUserIdAndAnimeTitleContainingIgnoreCase(UUID userId, String keyword);
}