package com.siciliancodes.anisyncbackend.entity;

import jakarta.persistence.*;

@Entity
public class Manga {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String title;
    private Integer totalChapters;
    @Enumerated(EnumType.STRING)
    private Genre genre;
    @Enumerated(EnumType.STRING)
    private Status status;


    public Manga() {};

    public Manga(String title,Integer totalChapters, Genre genre, Status status ){
        this.title = title;
        this.genre = genre;
        this.status = status;
        this.totalChapters = totalChapters;
    }
    // Getters

    public String getTitle() {
        return title;
    }

    public Genre getGenre(){
        return genre;
    }

    public Status getStatus(){
        return status;
    }

    public Integer getChapters(){
        return totalChapters;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setGenre(Genre Genre){
        this.genre = genre;
    }

    public void setStatus(Status Status){
        this.status = status;
    }

    public void setEpisodes(Integer totalEpisodes){
        this.totalChapters = totalChapters;
    }


}
