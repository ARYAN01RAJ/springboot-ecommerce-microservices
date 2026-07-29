package com.example.notification.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import com.example.notification.entity.Notifications;
@Service
public class InventoryListener {
    @Autowired
    NotificationService service;
    @KafkaListener(
            topics = "notification-test-one-topic",
            groupId = "inventory-group",
            containerFactory = "kafkaListenerContainerFactory"
    )
    public void consume(Notifications noti) {
        service.saveNotification(noti);
    }
}