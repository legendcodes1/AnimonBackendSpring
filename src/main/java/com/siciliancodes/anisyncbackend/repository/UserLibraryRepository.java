package com.siciliancodes.anisyncbackend.repository;

import com.siciliancodes.anisyncbackend.entity.UserLibrary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserLibraryRepository extends JpaRepository<UserLibrary, UUID> {  // ✅ Correct entity

    // ✅ Find all library items for a user
    List<UserLibrary> findByUserId(UUID userId);

    // ✅ Find all library items for a user by status
    List<UserLibrary> findByUserIdAndStatus(UUID userId, String status);

    // ✅ Find all library items for a user by type (anime/manga)
    List<UserLibrary> findByUserIdAndType(UUID userId, String type);

    // ✅ Find specific anime/manga in user's library
    Optional<UserLibrary> findByUserIdAndAnimeId(UUID userId, String animeId);

    // ✅ Check if user already has this anime/manga
    boolean existsByUserIdAndAnimeId(UUID userId, String animeId);

    // ✅ Count items by status for a user
    long countByUserIdAndStatus(UUID userId, String status);

    // ✅ Find by type and status
    List<UserLibrary> findByUserIdAndTypeAndStatus(UUID userId, String type, String status);

    // ✅ Search user's library by title
    List<UserLibrary> findByUserIdAndAnimeTitleContainingIgnoreCase(UUID userId, String keyword);
}