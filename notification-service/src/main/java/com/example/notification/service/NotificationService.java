package com.example.notification.service;
import java.util.List;
import com.example.notification.entity.Notifications;
public interface NotificationService {
    public List<Notifications> getAllNotifications();
    public void saveNotification(Notifications noti);
    public String deleteNotification(long id);
}