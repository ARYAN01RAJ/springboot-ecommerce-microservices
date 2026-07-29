package com.example.inventory.service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.inventory.dto.InventoryDto;
import com.example.inventory.dto.NotificationDto;
import com.example.inventory.entity.Inventory;
import com.example.inventory.exception.InventoryAlreadyExistsException;
import com.example.inventory.exception.InventoryNotFoundException;
import com.example.inventory.repository.InventoryRepository;

@Service
public class InventoryServiceImpl implements InventoryService {
    @Autowired
    InventoryRepository repo;
    @Autowired
    NotificationProducer notificationProducer;
    @Autowired
    ModelMapper mapper;

    @Override
    public List<InventoryDto> getInventoryRecord() {
        // TODO Auto-generated method stub
        List<Inventory> response = repo.findAll();
        List<InventoryDto> responseDto = response.stream()
                .map(x->mapper.map(x, InventoryDto.class))
                .collect(Collectors.toList());
        return responseDto;
    }
    @Override
    public InventoryDto addInventory(InventoryDto stock) {
        // TODO Auto-generated method stub
        Inventory exist = repo.findByItemId(stock.getItemId());
        if (exist != null) {
            throw new InventoryAlreadyExistsException("Inventory record already exists for itemId: " + stock.getItemId());
        }
        Inventory newItem = mapper.map(stock, Inventory.class);
        return mapper.map(repo.save(newItem), InventoryDto.class);
    }
    @Override
    public InventoryDto updateInventory(InventoryDto stock) {
        // TODO Auto-generated method stub
        Inventory existingStock = repo.findByItemId(stock.getItemId());
        if (existingStock != null) {
            int oldQuantity = existingStock.getQuantity();
            int newQuantity = stock.getQuantity();
            existingStock.setQuantity(newQuantity);
            if (stock.getWarehouseLocation() != null) {
                existingStock.setWarehouseLocation(stock.getWarehouseLocation());
            }
            Inventory updatedStock = repo.save(existingStock);
            InventoryDto response = mapper.map(updatedStock, InventoryDto.class);
            if(oldQuantity >= 100 && newQuantity < 100) {
                sendKafkaEvent("Low Stock", updatedStock);
            } else if(oldQuantity < 100 && newQuantity >= 100) {
                sendKafkaEvent("Replenished Stock", updatedStock);
            }
            return response;
        } else {
            throw new InventoryNotFoundException("Cannot update. Inventory record not found for itemId: " + stock.getItemId());
        }
    }
    @Override
    public Optional<InventoryDto> deleteRecord(long itemId) {
        // TODO Auto-generated method stub
        Inventory exist = repo.findByItemId(itemId);
        if (exist != null) {
            repo.deleteById(exist.getId());
        } else {
            throw new InventoryNotFoundException("Cannot delete. Inventory record not found for itemId: " + itemId);
        }
        return Optional.empty();
    }
    public void sendKafkaEvent(String event_type, Inventory updatedStock) {
        NotificationDto alert = new NotificationDto();
        alert.setEventType(event_type);
        alert.setItemId(updatedStock.getItemId());
        alert.setMessage("Stock for item:"+updatedStock.getItemId()+" is now "+updatedStock.getQuantity());
        alert.setRecipient("inventory.manager@Company.com");
        notificationProducer.sendOrder(alert);
    }
}