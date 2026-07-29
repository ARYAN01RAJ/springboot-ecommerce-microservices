package com.example.inventory.service;
import java.util.List;
import java.util.Optional;
import com.example.inventory.dto.InventoryDto;
import com.example.inventory.entity.Inventory;
public interface InventoryService {
    public List<InventoryDto> getInventoryRecord();
    public InventoryDto addInventory(InventoryDto stock);
    public InventoryDto updateInventory(InventoryDto stock);
    public Optional<InventoryDto> deleteRecord(long itemId);
}