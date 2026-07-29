package com.example.notification.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.time.LocalDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.notification.entity.Notifications;
import com.example.notification.repository.NotificationRepository;
@ExtendWith(MockitoExtension.class)
public class NotificationServiceImplTest {
    @Mock
    private NotificationRepository repo;
    @InjectMocks
    private NotificationServiceImpl service;
    private Notifications lowStockNoti;
    private Notifications replenishedNoti;
    @BeforeEach
    void setUp() {
        lowStockNoti = new Notifications("Low Stock", "admin@test.com", "Warning", LocalDateTime.now());
        lowStockNoti.setItemId(10L);
        replenishedNoti = new Notifications("Replenished Stock", "admin@test.com", "Resolved", LocalDateTime.now());
        replenishedNoti.setItemId(10L);
    }
    @Test
    void testSaveNotification_LowStock_CreatesNewActiveAlert() {
        when(repo.findByItemIdAndStatus(10L, "ACTIVE")).thenReturn(null);
        service.saveNotification(lowStockNoti);
        assertEquals("ACTIVE", lowStockNoti.getStatus());
        verify(repo, times(1)).save(lowStockNoti);
    }
    @Test
    void testSaveNotification_ReplenishedStock_ResolvesActiveAlert() {
        Notifications existingActive = new Notifications();
        existingActive.setStatus("ACTIVE");
        when(repo.findByItemIdAndStatus(10L, "ACTIVE")).thenReturn(existingActive);
        service.saveNotification(replenishedNoti);
        assertEquals("RESOLVED", existingActive.getStatus());
        verify(repo, times(1)).save(existingActive);
    }
    @Test
    void testDeleteNotification_Exists_ReturnsDeletedMessage() {
        when(repo.existsById(1L)).thenReturn(true).thenReturn(false);
        String result = service.deleteNotification(1L);
        assertEquals("Notification with id:1 deleted", result);
        verify(repo, times(1)).deleteById(1L);
    }
}