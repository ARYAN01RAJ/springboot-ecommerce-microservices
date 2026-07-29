package com.example.item.service;
import java.util.List;
import com.example.item.dto.ItemDto;
import com.example.item.dto.ItemListDto;

public interface ItemService {
    public List<ItemListDto> getAllItems();
    public ItemDto addItem(ItemDto dto);
    public String deleteItem(long id);
    public ItemListDto updateItem(ItemListDto dto);
}