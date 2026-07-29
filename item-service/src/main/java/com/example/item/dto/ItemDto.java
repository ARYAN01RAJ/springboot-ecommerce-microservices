package com.example.item.dto;
import java.time.LocalDateTime;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
public class ItemDto {

    Long itemId;

    @NotBlank(message = "item name cannot be empty")
    @Size(max = 150, message = "Item name cannot exceed 150 characters")
    String itemName;

    @NotBlank(message = "item description cannot be empty")
    String description;

    @NotBlank(message = "item category cannot be empty")
    @Size(max = 100, message = "Category cannot exceed 100 characters")
    String category;

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 0, message = "Quantity cannot be negative")
    Integer quantity;

    @NotBlank(message = "Warehouse location cannot be empty")
    @Size(max = 100, message = "Warehouse location cannot exceed 100 characters")
    String warehouseLocation;

    LocalDateTime createdAt;
    LocalDateTime lastUpdated;

    public ItemDto() {
        super();
    }

    public ItemDto(@NotBlank(message = "item name cannot be empty") String itemName,
                   @NotBlank(message = "item description cannot be empty") String description,
                   @NotBlank(message = "item category cannot be empty") String category, Integer quantity,
                   String warehouseLocation) {
        super();
        this.itemName = itemName;
        this.description = description;
        this.category = category;
        this.quantity = quantity;
        this.warehouseLocation = warehouseLocation;
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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getWarehouseLocation() {
        return warehouseLocation;
    }

    public void setWarehouseLocation(String warehouseLocation) {
        this.warehouseLocation = warehouseLocation;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}