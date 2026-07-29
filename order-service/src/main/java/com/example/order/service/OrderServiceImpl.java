package com.example.order.service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.order.dto.InventoryDto;
import com.example.order.dto.OrderDto;
import com.example.order.entity.Orders;
import com.example.order.exception.InsufficientStockException;
import com.example.order.exception.ResourceNotFoundException;
import com.example.order.repository.OrderRepository;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    FeignProxy proxy;
    @Autowired
    OrderRepository repo;
    @Autowired
    ModelMapper mapper;
    @Override
    public List<OrderDto> getAllOrders() {
        // TODO Auto-generated method stub
        return repo.findAll().stream()
                .map(x-> mapper.map(x, OrderDto.class))
                .collect(Collectors.toList());
    }
    @Override
    public Optional<Orders> getOrder(long id) {
        // TODO Auto-generated method stub
        return repo.findById(id);
    }
    @Override
    public OrderDto placeOrder(OrderDto sent) {
        // TODO Auto-generated method stub
        Orders order = mapper.map(sent, Orders.class);
        List<InventoryDto> current = proxy.getAll().stream()
                .filter(x -> x.getItemId().equals(order.getItemId()))
                .collect(Collectors.toList());
        if (current == null || current.isEmpty()) {
            throw new ResourceNotFoundException("Inventory not found for item ID: " + order.getItemId());
        }
        InventoryDto inventoryToUpdate = current.get(0);
        if (inventoryToUpdate.getQuantity() < order.getQuantity()) {
            throw new InsufficientStockException("Order failed. Requested quantity (" +
                    order.getQuantity() + ") exceeds available stock (" +
                    inventoryToUpdate.getQuantity() + ").");
        }
        int newQuantity = inventoryToUpdate.getQuantity() - order.getQuantity();
        inventoryToUpdate.setQuantity(newQuantity);
        InventoryDto status = proxy.update(inventoryToUpdate);
        Orders savedOrder = repo.save(order);
        return mapper.map(savedOrder, OrderDto.class);
    }
    @Override
    public String deleteOrder(long id) {
        // TODO Auto-generated method stub
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("Cannot delete. Order with ID " + id + " not found.");
        }
        repo.deleteById(id);
        return "Order with id: " + id + " deleted successfully.";
    }
}