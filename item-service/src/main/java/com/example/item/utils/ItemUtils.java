package com.example.item.utils;
import java.util.List;
import java.util.stream.Collectors;
import com.example.item.dto.InventoryDto;
import com.example.item.dto.ItemDto;
import com.example.item.dto.ItemListDto;
import com.example.item.entity.Item;
public class ItemUtils {
    public static ItemDto toDto(Item item, InventoryDto inv) {
        ItemDto dto = new ItemDto();
        dto.setItemId(item.getItemId());
        dto.setCategory(item.getCategory());
        dto.setCreatedAt(item.getCreatedAt());
        dto.setDescription(item.getDescription());
        dto.setItemName(item.getItemName());
        dto.setQuantity(inv.getQuantity());
        dto.setWarehouseLocation(inv.getWarehouseLocation());
        dto.setLastUpdated(inv.getLastUpdated());
        return dto;
    }
    public static Item toEntity(ItemDto dto) {
        return new Item(dto.getItemName(),dto.getDescription(),dto.getCategory());
    }
    public static InventoryDto toInventoryDto(long itemId, int quantity, String warehouseLocation) {
        return new InventoryDto(itemId,quantity,warehouseLocation);
    }
}