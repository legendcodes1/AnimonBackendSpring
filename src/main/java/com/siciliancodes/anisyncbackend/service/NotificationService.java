package com.siciliancodes.anisyncbackend.service;

import com.siciliancodes.anisyncbackend.entity.Notifications;
import com.siciliancodes.anisyncbackend.entity.User;
import com.siciliancodes.anisyncbackend.repository.NotificationRepository;
import com.siciliancodes.anisyncbackend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NotificationService {

    private final NotificationRepository notificationRepository;  // ✅ Final + injected
    private final UserRepository userRepository;

    @Transactional
    public Notifications createNotification(UUID userId, String type, String title,
                                           String message, String actionUrl) {
        // Get user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + userId));

        // Create notification
        Notifications notification = Notifications.builder()
                .user(user)
                .type(type)
                .title(title)  // ✅ Added title
                .message(message)
                .actionUrl(actionUrl)
                .read(false)  // ✅ Default unread
                .build();

        return notificationRepository.save(notification);
    }

    public List<Notifications> getUserNotifications(UUID userId) {
        return notificationRepository.findByUserId(userId);
    }

    public List<Notifications> getUnreadNotifications(UUID userId) {
        return notificationRepository.findByUserIdAndReadFalse(userId);
    }

    @Transactional
    public Notifications markAsRead(UUID notificationId) {
        Notifications notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new IllegalArgumentException("Notification not found"));

        notification.setRead(true);
        return notificationRepository.save(notification);
    }

    @Transactional
    public void markAllAsRead(UUID userId) {
        List<Notifications> unreadNotifications = notificationRepository.findByUserIdAndReadFalse(userId);
        unreadNotifications.forEach(n -> n.setRead(true));
        notificationRepository.saveAll(unreadNotifications);
    }

    @Transactional
    public void deleteNotification(UUID notificationId) {
        if (!notificationRepository.existsById(notificationId)) {
            throw new IllegalArgumentException("Notification not found");
        }
        notificationRepository.deleteById(notificationId);
    }
}