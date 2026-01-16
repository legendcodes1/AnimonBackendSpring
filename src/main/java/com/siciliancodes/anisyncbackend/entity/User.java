package com.siciliancodes.anisyncbackend.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import lombok.AllArgsConstructor;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity
@Table(name = "users")
@Data                    // Lombok: generates getters, setters, toString, equals, hashCode
@NoArgsConstructor       // Lombok: no-arg constructor for JPA
@AllArgsConstructor      // Lombok: all-args constructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(unique = true, nullable = false)
    private String username;

    @Column(name = "password_hash")
    private String passwordHash;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(columnDefinition = "INTEGER DEFAULT 1")
    private Integer level = 1;

    @Column(columnDefinition = "INTEGER DEFAULT 0")
    private Integer xp = 0;

    @Column(name = "current_streak", columnDefinition = "INTEGER DEFAULT 0")
    private Integer currentStreak = 0;

    @Column(name = "longest_streak")
    private Integer longestStreak = 0;  // ✅ Stored in DB

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "last_watch_date")
    private LocalDate lastWatchDate;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;  // ✅ This is correct

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


}
