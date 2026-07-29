package com.example.order.dto;
import java.time.LocalDateTime;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
public class InventoryDto {
    private Long id;

    @NotNull(message = "itemId cannot be null")
    @Positive(message = "itemId must be a valid positive number")
    private Long itemId;

    @NotNull(message = "Quantity cannot be null")
    @Min(value = 0, message = "Quantity cannot be negative")
    private Integer quantity;

    @NotBlank(message = "Warehouse location cannot be blank")
    @Size(max = 100, message = "Warehouse location cannot exceed 100 characters")
    private String warehouseLocation;

    private LocalDateTime lastUpdated;

    public InventoryDto() {
        super();
    }
    public InventoryDto(Long itemId, Integer quantity, String warehouseLocation) {
        super();
        this.itemId = itemId;
        this.quantity = quantity;
        this.warehouseLocation = warehouseLocation;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getItemId() {
        return itemId;
    }
    public void setItemId(Long itemId) {
        this.itemId = itemId;
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
    public LocalDateTime getLastUpdated() {
        return lastUpdated;
    }
    public void setLastUpdated(LocalDateTime lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
}