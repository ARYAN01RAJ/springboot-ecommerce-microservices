package com.example.inventory.dto;
import java.time.LocalDateTime;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
public class NotificationDto {

    @NotNull(message = "itemId cannot be null")
    @Positive(message = "itemId must be a valid positive number")
    private Long itemId;

    @NotBlank(message = "EventType cannot be blank")
    @Size(max = 100, message = "EventType cannot exceed 100 characters")
    private String eventType;

    @NotBlank(message = "Recipient cannot be blank")
    @Size(max = 150, message = "Recipient cannot exceed 150 characters")
    private String recipient;

    @NotBlank(message = "Message cannot be blank")
    private String message;
    private LocalDateTime sentAt;
    public NotificationDto() {
        super();
    }
    public NotificationDto(String eventType, String recipient, String message, LocalDateTime sentAt) {
        super();
        this.eventType = eventType;
        this.recipient = recipient;
        this.message = message;
        this.sentAt = sentAt;
    }
    public Long getItemId() {
        return itemId;
    }
    public void setItemId(Long itemId) {
        this.itemId = itemId;
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
}