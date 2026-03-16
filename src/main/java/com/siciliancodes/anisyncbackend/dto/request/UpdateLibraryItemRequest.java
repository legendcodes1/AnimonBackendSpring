package com.siciliancodes.anisyncbackend.dto.request;

import lombok.Data;

@Data
public class UpdateLibraryItemRequest {
    private String status;
    private Integer episodesWatched;
    private Integer chaptersRead;
    private Double rating;
    private String notes;
}