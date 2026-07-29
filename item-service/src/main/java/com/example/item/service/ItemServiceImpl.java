package com.example.item.service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.item.dto.InventoryDto;
import com.example.item.dto.ItemDto;
import com.example.item.dto.ItemListDto;
import com.example.item.entity.Item;
import com.example.item.exception.ItemAlreadyExistsException;
import com.example.item.exception.ItemNotFoundException;
import com.example.item.repository.ItemRepository;
import com.example.item.utils.ItemUtils;
@Service
public class ItemServiceImpl implements ItemService {
    @Autowired
    ItemRepository repo;
    @Autowired
    FeignProxy proxy;
    @Autowired
    ModelMapper mapper;

    @Override
    public List<ItemListDto> getAllItems() {
        // TODO Auto-generated method stub
        List<Item> item = repo.findAll();
        List<ItemListDto> dto = item.stream()
                .map(x->mapper.map(x, ItemListDto.class))
                .collect(Collectors.toList());
        return dto;
    }
    @Override
    public ItemDto addItem(ItemDto dto) {
        // TODO Auto-generated method stub
        Item it = repo.findByItemName(dto.getItemName());
        if(it == null) {
            Item item = mapper.map(dto, Item.class);
            repo.save(item);
            Item savedItem = repo.findByItemName(item.getItemName());
            InventoryDto invDto = ItemUtils.toInventoryDto(savedItem.getItemId(), dto.getQuantity(), dto.getWarehouseLocation());
            InventoryDto respDto = proxy.addItem(invDto);
            return ItemUtils.toDto(savedItem, respDto);
        } else {
            throw new ItemAlreadyExistsException("Item with name '" + dto.getItemName() + "' already exists.");
        }
    }
    @Override
    public String deleteItem(long id) {
        // TODO Auto-generated method stub
        if(!repo.existsById(id)) {
            throw new ItemNotFoundException("Cannot delete. Item with ID " + id + " not found.");
        }
        // If we reach here, the item exists. Delete it and trigger the inventory deletion.
        repo.deleteById(id);
        proxy.delete(id);
        return "Item with ID: " + id + " deleted successfully.";
    }
    @Override
    public ItemListDto updateItem(ItemListDto dto) {
        // TODO Auto-generated method stub
        if (dto.getItemId() == null || !repo.existsById(dto.getItemId())) {
            throw new ItemNotFoundException("Cannot update. Item with ID " + dto.getItemId() + " not found.");
        }
        Item item = mapper.map(dto, Item.class);
        Item response = repo.save(item);
        return mapper.map(response, ItemListDto.class);
    }
}