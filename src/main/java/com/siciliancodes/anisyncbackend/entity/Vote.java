package com.siciliancodes.anisyncbackend.entity;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer stars;
    private String review;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne
    @JoinColumn(name = "anime_id")
    private Anime anime;

    protected Vote() {}

    public Vote(Integer stars, String review, Group group, Anime anime) {
        this.stars = stars;
        this.review = review;
        this.group = group;
        this.anime = anime;
    }

    public Integer getStars() {
        return stars;
    }

    public String getReview() {
        return review;
    }
}
