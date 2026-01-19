package com.siciliancodes.anisyncbackend.repository;

import com.siciliancodes.anisyncbackend.entity.Anime;
import com.siciliancodes.anisyncbackend.entity.Genre;
import com.siciliancodes.anisyncbackend.entity.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface AnimeRepository extends JpaRepository<Anime, UUID> {


    Optional<Anime> findByAnimeId(UUID uuid);

    List<Anime> findAnimeByGenre(Genre genre);

    List<Anime> findByAnimeNameContainingIgnoreCase(String keyword);

    List<Anime> findAnimeByStatus(Status status);

}
