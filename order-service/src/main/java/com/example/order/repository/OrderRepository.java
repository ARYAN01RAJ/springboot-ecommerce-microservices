package com.example.order.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.order.entity.Orders;

public interface OrderRepository extends JpaRepository<Orders, Long>{

}