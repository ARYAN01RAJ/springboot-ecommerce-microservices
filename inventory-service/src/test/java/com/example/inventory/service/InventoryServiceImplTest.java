package com.example.inventory.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import com.example.inventory.dto.InventoryDto;
import com.example.inventory.dto.NotificationDto;
import com.example.inventory.entity.Inventory;
import com.example.inventory.exception.InventoryAlreadyExistsException;
import com.example.inventory.exception.InventoryNotFoundException;
import com.example.inventory.repository.InventoryRepository;
@ExtendWith(MockitoExtension.class)
public class InventoryServiceImplTest {
    @Mock
    private InventoryRepository repo;
    @Mock
    private NotificationProducer notificationProducer;
    @Mock
    private ModelMapper mapper;
    @InjectMocks
    private InventoryServiceImpl service;
    private InventoryDto inventoryDto;
    private Inventory inventoryEntity;
    @BeforeEach
    void setUp() {
        inventoryDto = new InventoryDto(10L, 150, "Warehouse A");
        inventoryDto.setId(1L);
        inventoryEntity = new Inventory(10L, 150, "Warehouse A");
        inventoryEntity.setId(1L);
    }
    @Test
    void testAddInventory_Success() {
        when(repo.findByItemId(10L)).thenReturn(null);
        when(mapper.map(inventoryDto, Inventory.class)).thenReturn(inventoryEntity);
        when(repo.save(inventoryEntity)).thenReturn(inventoryEntity);
        when(mapper.map(inventoryEntity, InventoryDto.class)).thenReturn(inventoryDto);
        InventoryDto result = service.addInventory(inventoryDto);
        assertNotNull(result);
        assertEquals(10L, result.getItemId());
        verify(repo, times(1)).save(inventoryEntity);
    }
    @Test
    void testAddInventory_AlreadyExists_ThrowsException() {
        when(repo.findByItemId(10L)).thenReturn(inventoryEntity);
        assertThrows(InventoryAlreadyExistsException.class, () -> {
            service.addInventory(inventoryDto);
        });
        verify(repo, never()).save(any());
    }
    @Test
    void testUpdateInventory_LowStock_TriggersKafkaEvent() {
        InventoryDto updateDto = new InventoryDto(10L, 50, "Warehouse A");
        when(repo.findByItemId(10L)).thenReturn(inventoryEntity);
        when(repo.save(any(Inventory.class))).thenReturn(inventoryEntity);
        when(mapper.map(inventoryEntity, InventoryDto.class)).thenReturn(updateDto);
        InventoryDto result = service.updateInventory(updateDto);
        assertNotNull(result);
        verify(notificationProducer, times(1)).sendOrder(any(NotificationDto.class));
    }
    @Test
    void testUpdateInventory_NotFound_ThrowsException() {
        when(repo.findByItemId(99L)).thenReturn(null);
        InventoryDto updateDto = new InventoryDto(99L, 50, "Warehouse A");
        assertThrows(InventoryNotFoundException.class, () -> {
            service.updateInventory(updateDto);
        });
    }
    @Test
    void testDeleteRecord_Success() {
        when(repo.findByItemId(10L)).thenReturn(inventoryEntity);
        Optional<InventoryDto> result = service.deleteRecord(10L);
        assertTrue(result.isEmpty());
        verify(repo, times(1)).deleteById(1L);
    }
}