package com.example.notification.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.notification.entity.Notifications;
import com.example.notification.service.NotificationService;
@RestController
@RequestMapping("/api/notifications")
public class NotificationController {
    @Autowired
    NotificationService service;
    @GetMapping("/allNotifications")
    public List<Notifications> getAll(){
        return service.getAllNotifications();
    }
    @DeleteMapping("/delete/{id}")
    public String deleteNotification(@PathVariable long id) {
        return service.deleteNotification(id);
    }
}