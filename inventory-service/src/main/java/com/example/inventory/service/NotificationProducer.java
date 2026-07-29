package com.example.inventory.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.example.inventory.dto.NotificationDto;

@Service
public class NotificationProducer {
    @Autowired
    private KafkaTemplate<String, NotificationDto> kafkaTemplate;
    public void sendOrder(NotificationDto noti) {
        kafkaTemplate.send("notification-test-one-topic",
                noti.getEventType(), noti);
        System.out.println("Notification sent");
    }
}