package com.siciliancodes.anisyncbackend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "groups")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Group {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;  // ✅ Just "id", not "groupId"

    @Column(nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;  // ✅ Added description

    @Column(name = "avatar_url")
    private String avatarUrl;  // ✅ Camel case

    @ManyToOne(fetch = FetchType.LAZY)  // ✅ Who created the group
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    // ✅ One group has many members (bidirectional relationship)
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL, orphanRemoval = true)
    @ToString.Exclude  // ✅ Prevent circular toString
    @Builder.Default
    private Set<GroupMember> members = new HashSet<>();

    // ✅ One group has many voting sessions
    @OneToMany(mappedBy = "group", cascade = CascadeType.ALL)
    @ToString.Exclude
    @Builder.Default
    private Set<VotingSession> votingSessions = new HashSet<>();

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    // ✅ Helper method to get member count
    public int getMemberCount() {
        return members.size();
    }
}