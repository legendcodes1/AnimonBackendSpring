package com.siciliancodes.anisyncbackend.repository;

import com.siciliancodes.anisyncbackend.entity.Notifications;  // ✅ YOUR entity
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NotificationRepository extends JpaRepository<Notifications, UUID> {

    // Find all notifications for a user
    List<Notifications> findByUserId(UUID userId);

    // Find unread notifications for a user
    List<Notifications> findByUserIdAndReadFalse(UUID userId);

    // Find notifications by type for a user
    List<Notifications> findByUserIdAndType(UUID userId, String type);

}