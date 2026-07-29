package com.example.notification.service;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.notification.entity.Notifications;
import com.example.notification.repository.NotificationRepository;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    NotificationRepository repo;

    @Override
    public List<Notifications> getAllNotifications() {
        // TODO Auto-generated method stub
        return repo.findAll();
        
    }
    @Override
    public void saveNotification(Notifications noti) {
        // TODO Auto-generated method stub
        if("Low Stock".equals(noti.getEventType())) {
            Notifications existing = repo.findByItemIdAndStatus(noti.getItemId(), "ACTIVE");
            if(existing==null) {
                noti.setStatus("ACTIVE");
                noti.setSentAt(LocalDateTime.now());
                repo.save(noti);
                System.out.println(noti);
            }
        }else if("Replenished Stock".equals(noti.getEventType())) {
            Notifications existing = repo.findByItemIdAndStatus(noti.getItemId(), "ACTIVE");
            if(existing!=null) {
                existing.setStatus("RESOLVED");
                repo.save(existing);
                System.out.println(noti);
            }
        }else {
            System.out.println("No active alert to resolve for Item: " + noti.getItemId());
        }
    }
    @Override
    public String deleteNotification(long id) {
        // TODO Auto-generated method stub
        if(repo.existsById(id)) {
            repo.deleteById(id);
            if(repo.existsById(id)) {
                return "Notification with id:"+id+" not deleted";
            }else {
                return "Notification with id:"+id+" deleted";
            }
        }
        return "Notification with id:"+id+" does not exist";
    }
}