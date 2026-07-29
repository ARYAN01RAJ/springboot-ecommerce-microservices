package com.example.notification.entity;
import java.time.LocalDateTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name="notifications")
public class Notifications {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    long id;
    long itemId;
    String eventType;
    String recipient;
    String message;
    @Column(name="sent_at")
    LocalDateTime sentAt;
    String status;
    public Notifications() {
        super();
// TODO Auto-generated constructor stub
    }
    public Notifications(String eventType, String recipient, String message, LocalDateTime sentAt) {
        super();
        this.eventType = eventType;
        this.recipient = recipient;
        this.message = message;
        this.sentAt= sentAt;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public long getItemId() {
        return itemId;
    }
    public void setItemId(long itemId) {
        this.itemId = itemId;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getRecipient() {
        return recipient;
    }
    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public String getEventType() {
        return eventType;
    }
    public void setEventType(String eventType) {
        this.eventType = eventType;
    }
    public LocalDateTime getSentAt() {
        return sentAt;
    }
    public void setSentAt(LocalDateTime sentAt) {
        this.sentAt = sentAt;
    }
    @Override
    public String toString() {
        return "Notifications [id=" + id + ", itemId=" + itemId + ", eventType=" + eventType + ", recipient="
                + recipient + ", message=" + message + ", sentAt=" + sentAt + ", status=" + status + "]";
    }
}