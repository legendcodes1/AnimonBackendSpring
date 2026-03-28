package com.siciliancodes.anisyncbackend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import java.time.LocalDateTime;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "group_members")
public class GroupMember {

    @EmbeddedId  // ✅ Composite primary key
    private GroupMemberId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("groupId")  //
    @JoinColumn(name = "group_id")
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")  // ✅ Maps to userId in composite key
    @JoinColumn(name = "user_id")
    private User user;

    @Column(nullable = false, length = 20)
    private String role = "member";
    // Valid values: admin, moderator, member

    @CreationTimestamp
    @Column(name = "joined_at", updatable = false)
    private LocalDateTime joinedAt;
}