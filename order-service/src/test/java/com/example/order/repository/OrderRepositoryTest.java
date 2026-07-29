package com.example.order.repository;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import com.example.order.entity.Orders;
@org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest
public class OrderRepositoryTest {
@Autowired
private OrderRepository repo;
@Test
void testSaveAndFindOrder_Success() {
Orders newOrder = new Orders(55L, 10, "London Warehouse");
Orders savedOrder = repo.save(newOrder);
Optional<Orders> retrievedOrder = repo.findById(savedOrder.getId());
assertTrue(retrievedOrder.isPresent());
assertEquals(55L, retrievedOrder.get().getItemId());
assertEquals(10, retrievedOrder.get().getQuantity());
assertEquals("London Warehouse", retrievedOrder.get().getLocation());
}
@Test
void testDeleteOrder_Success() {
Orders savedOrder = repo.save(new Orders(88L, 2, "Paris"));
long id = savedOrder.getId();
repo.deleteById(id);
Optional<Orders> deletedOrder = repo.findById(id);
assertFalse(deletedOrder.isPresent());
}
@Test
void testFindAllOrders_Success() {
repo.save(new Orders(1L, 5, "Location A"));
repo.save(new Orders(2L, 10, "Location B"));
List<Orders> allOrders = repo.findAll();
assertEquals(2, allOrders.size());
}
}