package com.siciliancodes.anisyncbackend.controller;

import com.siciliancodes.anisyncbackend.dto.request.AddToLibraryRequest;
import com.siciliancodes.anisyncbackend.dto.request.UpdateLibraryItemRequest;
import com.siciliancodes.anisyncbackend.dto.response.LibraryStatsResponse;
import com.siciliancodes.anisyncbackend.dto.response.UserLibraryResponse;
import com.siciliancodes.anisyncbackend.entity.UserLibrary;
import com.siciliancodes.anisyncbackend.service.UserLibraryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users/{userId}/library")  // ✅ RESTful URL structure
@RequiredArgsConstructor
public class UserLibraryController {

    private final UserLibraryService userLibraryService;

    // Add anime/manga to library
    @PostMapping
    public ResponseEntity<UserLibraryResponse> addToLibrary(
            @PathVariable UUID userId,
            @Valid @RequestBody AddToLibraryRequest request) {

        UserLibrary library = userLibraryService.addToLibrary(
                userId,
                request.getAnimeId(),
                request.getAnimeTitle(),
                request.getType(),
                request.getStatus(),
                request.getAnimePoster()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(mapToResponse(library));
    }

    // Get user's full library
    @GetMapping
    public ResponseEntity<List<UserLibraryResponse>> getUserLibrary(@PathVariable UUID userId) {
        List<UserLibrary> library = userLibraryService.getUserLibrary(userId);
        List<UserLibraryResponse> responses = library.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    // Get library filtered by status
    @GetMapping("/status/{status}")
    public ResponseEntity<List<UserLibraryResponse>> getLibraryByStatus(
            @PathVariable UUID userId,
            @PathVariable String status) {

        List<UserLibrary> library = userLibraryService.getUserLibraryByStatus(userId, status);
        List<UserLibraryResponse> responses = library.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    // Get library filtered by type (anime/manga)
    @GetMapping("/type/{type}")
    public ResponseEntity<List<UserLibraryResponse>> getLibraryByType(
            @PathVariable UUID userId,
            @PathVariable String type) {

        List<UserLibrary> library = userLibraryService.getUserLibraryByType(userId, type);
        List<UserLibraryResponse> responses = library.stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
        return ResponseEntity.ok(responses);
    }

    // Get specific item from library
    @GetMapping("/{animeId}")
    public ResponseEntity<UserLibraryResponse> getLibraryItem(
            @PathVariable UUID userId,
            @PathVariable String animeId) {

        UserLibrary library = userLibraryService.getLibraryItem(userId, animeId);
        return ResponseEntity.ok(mapToResponse(library));
    }

    // Update library item
    @PutMapping("/{animeId}")
    public ResponseEntity<UserLibraryResponse> updateLibraryItem(
            @PathVariable UUID userId,
            @PathVariable String animeId,
            @Valid @RequestBody UpdateLibraryItemRequest request) {

        UserLibrary library = userLibraryService.updateLibraryItem(
                userId,
                animeId,
                request.getStatus(),
                request.getEpisodesWatched(),
                request.getRating()
        );

        return ResponseEntity.ok(mapToResponse(library));
    }

    // Remove from library
    @DeleteMapping("/{animeId}")
    public ResponseEntity<Void> removeFromLibrary(
            @PathVariable UUID userId,
            @PathVariable String animeId) {

        userLibraryService.removeFromLibrary(userId, animeId);
        return ResponseEntity.noContent().build();
    }

    // Get library statistics
    @GetMapping("/stats")
    public ResponseEntity<LibraryStatsResponse> getLibraryStats(@PathVariable UUID userId) {
        long watching = userLibraryService.countByStatus(userId, "watching");
        long completed = userLibraryService.countByStatus(userId, "completed");
        long planToWatch = userLibraryService.countByStatus(userId, "plan_to_watch");
        long dropped = userLibraryService.countByStatus(userId, "dropped");

        LibraryStatsResponse stats = LibraryStatsResponse.builder()
                .watching(watching)
                .completed(completed)
                .planToWatch(planToWatch)
                .dropped(dropped)
                .total(watching + completed + planToWatch + dropped)
                .build();

        return ResponseEntity.ok(stats);
    }

    // Helper: Entity → DTO
    private UserLibraryResponse mapToResponse(UserLibrary library) {
        return UserLibraryResponse.builder()
                .id(library.getId())
                .animeId(library.getAnimeId())
                .animeTitle(library.getAnimeTitle())
                .type(library.getType())
                .status(library.getStatus())
                .animePoster(library.getAnimePoster())
                .rating(library.getRating())
                .episodesWatched(library.getEpisodesWatched())
                .totalEpisodes(library.getTotalEpisodes())
                .notes(library.getNotes())
                .addedAt(library.getAddedAt())
                .build();
    }
}