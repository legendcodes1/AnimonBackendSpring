package com.siciliancodes.anisyncbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "notifications")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notifications {  // ✅ Singular name (not Notifications)

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)  // ✅ UUID, not long
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)  // ✅ Relationship to User
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false, length = 50)
    private String type;  // ✅ streak_risk, group_activity, etc.

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Column(name = "action_url")
    private String actionUrl;  // ✅ Camel case

    @Column(nullable = false)
    private Boolean read = false;  // ✅ Default value

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;  // ✅ LocalDateTime (not LocalDate)
}