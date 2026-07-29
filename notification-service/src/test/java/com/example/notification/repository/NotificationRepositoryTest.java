package com.example.notification.repository;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
// Spring Boot 4 Import
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import com.example.notification.entity.Notifications;
@DataJpaTest
public class NotificationRepositoryTest {
    @Autowired
    private NotificationRepository repo;
    @Test
    void testFindByItemIdAndStatus_Success() {
        Notifications noti = new Notifications("Low Stock", "manager@test.com", "Stock is low", LocalDateTime.now());
        noti.setItemId(55L);
        noti.setStatus("ACTIVE");
        repo.save(noti);
        Notifications found = repo.findByItemIdAndStatus(55L, "ACTIVE");
        assertNotNull(found);
        assertEquals("Low Stock", found.getEventType());
    }
    @Test
    void testFindByItemIdAndStatus_NotFound() {
        Notifications found = repo.findByItemIdAndStatus(999L, "ACTIVE");
        assertNull(found);
    }
}