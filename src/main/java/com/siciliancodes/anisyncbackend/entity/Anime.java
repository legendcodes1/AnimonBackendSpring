package com.siciliancodes.anisyncbackend.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
public class Anime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private Integer totalEpisodes;

    @Enumerated(EnumType.STRING)
    private Genre genre;

    @Enumerated(EnumType.STRING)
    private Status status;

    protected Anime() {}

    public Anime(String title, Genre genre, Status status, Integer totalEpisodes) {
        this.title = title;
        this.genre = genre;
        this.status = status;
        this.totalEpisodes = totalEpisodes;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setTotalEpisodes(Integer totalEpisodes) {
        this.totalEpisodes = totalEpisodes;
    }
}
