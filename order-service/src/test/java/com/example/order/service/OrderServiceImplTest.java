package com.example.order.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import com.example.order.dto.InventoryDto;
import com.example.order.dto.OrderDto;
import com.example.order.entity.Orders;
import com.example.order.exception.InsufficientStockException;
import com.example.order.exception.ResourceNotFoundException;
import com.example.order.repository.OrderRepository;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {
    @Mock
    private OrderRepository repo;
    @Mock
    private FeignProxy proxy;
    @Mock
    private ModelMapper mapper;
    @InjectMocks
    private OrderServiceImpl service;
    private OrderDto orderDto;
    private Orders orderEntity;
    private InventoryDto inventoryDto;
    @BeforeEach
    void setUp() {
        orderDto = new OrderDto(1L, 5, "New York");
        orderDto.setId(100L);
        orderEntity = new Orders(1L, 5, "New York");
        orderEntity.setId(100L);
        inventoryDto = new InventoryDto(1L, 20, "Warehouse A");
    }
    @Test
    void testGetAllOrders_Success_ReturnsList() {
        when(repo.findAll()).thenReturn(Arrays.asList(orderEntity));
        when(mapper.map(orderEntity, OrderDto.class)).thenReturn(orderDto);
        List<OrderDto> result = service.getAllOrders();
        assertEquals(1, result.size());
        assertEquals(100L, result.get(0).getId());
        verify(repo, times(1)).findAll();
    }
    @Test
    void testGetAllOrders_Empty_ReturnsEmptyList() {
        when(repo.findAll()).thenReturn(new ArrayList<>());
        List<OrderDto> result = service.getAllOrders();
        assertTrue(result.isEmpty());
        verify(repo, times(1)).findAll();
    }
    @Test
    void testGetOrder_Exists_ReturnsOrder() {
        when(repo.findById(100L)).thenReturn(Optional.of(orderEntity));
        Optional<Orders> result = service.getOrder(100L);
        assertTrue(result.isPresent());
        assertEquals(100L, result.get().getId());
    }
    @Test
    void testGetOrder_DoesNotExist_ReturnsEmpty() {
        when(repo.findById(999L)).thenReturn(Optional.empty());
        Optional<Orders> result = service.getOrder(999L);
        assertFalse(result.isPresent());
    }
    @Test
    void testPlaceOrder_Success_SavesOrderAndUpdatesInventory() {
        when(mapper.map(orderDto, Orders.class)).thenReturn(orderEntity);
        when(proxy.getAll()).thenReturn(Arrays.asList(inventoryDto));
        when(proxy.update(any(InventoryDto.class))).thenReturn(inventoryDto);
        when(repo.save(orderEntity)).thenReturn(orderEntity);
        when(mapper.map(orderEntity, OrderDto.class)).thenReturn(orderDto);
        OrderDto result = service.placeOrder(orderDto);
        assertNotNull(result);
        assertEquals(15, inventoryDto.getQuantity());
        verify(proxy, times(1)).update(any(InventoryDto.class));
        verify(repo, times(1)).save(orderEntity);
    }
    @Test
    void testPlaceOrder_InventoryNotFound_ThrowsException() {
        when(mapper.map(orderDto, Orders.class)).thenReturn(orderEntity);
        when(proxy.getAll()).thenReturn(new ArrayList<>());
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> {
            service.placeOrder(orderDto);
        });
        assertTrue(ex.getMessage().contains("Inventory not found for item ID: 1"));
        verify(repo, never()).save(any());
    }
    @Test
    void testPlaceOrder_InsufficientStock_ThrowsException() {
        orderDto.setQuantity(50);
        orderEntity.setQuantity(50);
        when(mapper.map(orderDto, Orders.class)).thenReturn(orderEntity);
        when(proxy.getAll()).thenReturn(Arrays.asList(inventoryDto));
        InsufficientStockException ex = assertThrows(InsufficientStockException.class, () -> {
            service.placeOrder(orderDto);
        });
        assertTrue(ex.getMessage().contains("exceeds available stock"));
        verify(proxy, never()).update(any());
        verify(repo, never()).save(any());
    }
    @Test
    void testDeleteOrder_Exists_DeletesSuccessfully() {
        when(repo.existsById(100L)).thenReturn(true);
        String result = service.deleteOrder(100L);
        assertEquals("Order with id: 100 deleted successfully.", result);
        verify(repo, times(1)).deleteById(100L);
    }
    @Test
    void testDeleteOrder_DoesNotExist_ThrowsException() {
        when(repo.existsById(999L)).thenReturn(false);
        ResourceNotFoundException ex = assertThrows(ResourceNotFoundException.class, () -> {
            service.deleteOrder(999L);
        });
        assertTrue(ex.getMessage().contains("Cannot delete. Order with ID 999 not found."));
        verify(repo, never()).deleteById(anyLong());
    }
}