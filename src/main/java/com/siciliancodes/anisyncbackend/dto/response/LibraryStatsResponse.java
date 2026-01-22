package com.siciliancodes.anisyncbackend.dto.response;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LibraryStatsResponse {
    private long watching;
    private long completed;
    private long planToWatch;
    private long dropped;
    private long total;
}