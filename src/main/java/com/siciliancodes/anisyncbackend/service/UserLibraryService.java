package com.siciliancodes.anisyncbackend.service;

import com.siciliancodes.anisyncbackend.entity.User;
import com.siciliancodes.anisyncbackend.entity.UserLibrary;
import com.siciliancodes.anisyncbackend.repository.UserLibraryRepository;
import com.siciliancodes.anisyncbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserLibraryService {

    private final UserLibraryRepository userLibraryRepository;
    private final UserRepository userRepository;

    @Transactional
    public UserLibrary addToLibrary(UUID userId, String animeId, String animeTitle,
                                    String type, String status, String animePoster) {
        // Check if user exists
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        // Check if already in library
        if (userLibraryRepository.existsByUserIdAndAnimeId(userId, animeId)) {
            throw new IllegalArgumentException("This anime/manga is already in your library");
        }

        // Create library entry
        UserLibrary library = UserLibrary.builder()
                .user(user)
                .animeId(animeId)
                .animeTitle(animeTitle)
                .type(type)  // "anime" or "manga"
                .status(status)  // "watching", "completed", etc.
                .animePoster(animePoster)
                .episodesWatched(0)
                .build();

        return userLibraryRepository.save(library);
    }

    public List<UserLibrary> getUserLibrary(UUID userId) {
        return userLibraryRepository.findByUserId(userId);
    }

    public List<UserLibrary> getUserLibraryByStatus(UUID userId, String status) {
        return userLibraryRepository.findByUserIdAndStatus(userId, status);
    }

    public List<UserLibrary> getUserLibraryByType(UUID userId, String type) {
        return userLibraryRepository.findByUserIdAndType(userId, type);
    }

    public UserLibrary getLibraryItem(UUID userId, String animeId) {
        return userLibraryRepository.findByUserIdAndAnimeId(userId, animeId)
                .orElseThrow(() -> new IllegalArgumentException("Item not found in library"));
    }

    @Transactional
    public UserLibrary updateLibraryItem(UUID userId, String animeId, String status,
                                         Integer episodesWatched, Integer rating) {
        UserLibrary library = getLibraryItem(userId, animeId);

        if (status != null) {
            library.setStatus(status);
        }

        if (episodesWatched != null) {
            library.setEpisodesWatched(episodesWatched);
        }

        if (rating != null) {
            library.setRating(rating);
        }

        return userLibraryRepository.save(library);
    }


    @Transactional
    public void removeFromLibrary(UUID userId, String animeId) {
        UserLibrary library = getLibraryItem(userId, animeId);
        userLibraryRepository.delete(library);
    }

    public long countByStatus(UUID userId, String status) {
        return userLibraryRepository.countByUserIdAndStatus(userId, status);
    }
}