package com.siciliancodes.anisyncbackend.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class UserLibraryResponse {
    private UUID id;
    private String animeId;
    private String animeTitle;
    private String type;
    private String status;
    private String animePoster;
    private Integer rating;
    private Integer episodesWatched;
    private Integer totalEpisodes;
    private String notes;
    private LocalDateTime addedAt;
}