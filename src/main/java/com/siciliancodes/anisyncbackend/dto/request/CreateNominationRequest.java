package com.siciliancodes.anisyncbackend.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.util.UUID;

@Data
public class CreateNominationRequest {
    @NotNull(message = "User ID is required")
    private UUID userId;

    @NotBlank(message = "Anime ID is required")
    private String animeId;

    @NotBlank(message = "Anime title is required")
    private String animeTitle;

    private String animePoster;

    @NotBlank(message = "Type is required")
    private String type;  // "anime" or "manga"

}
