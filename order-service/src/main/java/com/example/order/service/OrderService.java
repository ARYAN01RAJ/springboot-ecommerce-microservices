package com.example.order.service;
import java.util.List;
import java.util.Optional;
import com.example.order.dto.OrderDto;
import com.example.order.entity.Orders;

public interface OrderService {
    public List<OrderDto> getAllOrders();
    public Optional<Orders> getOrder(long id);
    public OrderDto placeOrder(OrderDto sent);
    public String deleteOrder(long id);
}