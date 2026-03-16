package com.siciliancodes.anisyncbackend.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddToLibraryRequest {

    @NotBlank(message = "Anime ID is required")
    private String animeId;

    @NotBlank(message = "Title is required")
    private String animeTitle;

    @NotBlank(message = "Type is required")
    private String type; // "anime" or "manga"

    @NotBlank(message = "Status is required")
    private String status; // "watching", "completed", etc.

    private String animePoster;

    // Add these fields
    private Double rating;
    private Integer episodesWatched;
    private Integer totalEpisodes;
    private Integer chaptersRead;
    private Integer totalChapters;
    private String notes;
}