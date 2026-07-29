package com.example.notification.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.notification.entity.Notifications;
public interface NotificationRepository extends JpaRepository<Notifications,Long> {
    public Notifications findByItemIdAndStatus(long itemId, String status);
}