package com.example.item.dto;
import java.time.LocalDateTime;
import jakarta.validation.constraints.Size;
public class ItemListDto {
    Long itemId;

    @Size(max = 150, message = "Item name cannot exceed 150 characters")
    String itemName;
    String description;

    @Size(max = 100, message = "Category cannot exceed 100 characters")
    String category;

    LocalDateTime createdAt;
    public ItemListDto() {
        super();
    }
    public ItemListDto(Long itemId, String itemName, String description, String category, LocalDateTime createdAt) {
        super();
        this.itemName = itemName;
        this.description = description;
        this.category = category;
    }
    public Long getItemId() {
        return itemId;
    }
    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }
    public String getItemName() {
        return itemName;
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}