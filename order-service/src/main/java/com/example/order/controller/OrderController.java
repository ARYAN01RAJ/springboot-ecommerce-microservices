package com.example.order.controller;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.order.dto.OrderDto;
import com.example.order.entity.Orders;
import com.example.order.service.OrderService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    OrderService service;
    @GetMapping("/getOrders")
    public List<OrderDto> getAll(){
        return service.getAllOrders();
    }
    @GetMapping("/getOrder/{id}")
    public Optional<Orders> getOrder(@PathVariable long id){
        return service.getOrder(id);
    }
    @PostMapping("/placeorder")
    public OrderDto addOrder(@Valid @RequestBody OrderDto sent){
        return service.placeOrder(sent);
    }
    @DeleteMapping("/delete/{id}")
    public String deleteOrder(@PathVariable long id){
        return service.deleteOrder(id);
    }
}