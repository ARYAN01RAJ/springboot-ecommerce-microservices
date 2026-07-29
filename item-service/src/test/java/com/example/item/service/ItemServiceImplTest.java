package com.example.item.service;
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
import com.example.item.dto.InventoryDto;
import com.example.item.dto.ItemDto;
import com.example.item.entity.Item;
import com.example.item.exception.ItemAlreadyExistsException;
import com.example.item.exception.ItemNotFoundException;
import com.example.item.repository.ItemRepository;
@ExtendWith(MockitoExtension.class)
public class ItemServiceImplTest {
    @Mock
    private ItemRepository repo;
    @Mock
    private FeignProxy proxy;
    @Mock
    private ModelMapper mapper;
    @InjectMocks
    private ItemServiceImpl service;
    private ItemDto itemDto;
    private Item itemEntity;
    private InventoryDto inventoryDto;
    @BeforeEach
    void setUp() {
        itemDto = new ItemDto("Monitor", "4K Monitor", "Electronics", 10, "Warehouse A");
        itemEntity = new Item("Monitor", "4K Monitor", "Electronics");
        itemEntity.setItemId(1L);
        inventoryDto = new InventoryDto(1L, 10, "Warehouse A");
    }
    @Test
    void testAddItem_Success() {
        when(repo.findByItemName("Monitor")).thenReturn(null).thenReturn(itemEntity);
        when(mapper.map(itemDto, Item.class)).thenReturn(itemEntity);
        when(proxy.addItem(any(InventoryDto.class))).thenReturn(inventoryDto);
        ItemDto result = service.addItem(itemDto);
        assertNotNull(result);
        assertEquals("Monitor", result.getItemName());
        verify(repo, times(1)).save(itemEntity);
        verify(proxy, times(1)).addItem(any(InventoryDto.class));
    }
    @Test
    void testAddItem_AlreadyExists_ThrowsException() {
        when(repo.findByItemName("Monitor")).thenReturn(itemEntity);
        assertThrows(ItemAlreadyExistsException.class, () -> {
            service.addItem(itemDto);
        });
        verify(repo, never()).save(any());
    }
    @Test
    void testDeleteItem_Success() {
        when(repo.existsById(1L)).thenReturn(true);
        when(proxy.delete(1L)).thenReturn(Optional.of(inventoryDto));
        String response = service.deleteItem(1L);
        assertEquals("Item with ID: 1 deleted successfully.", response);
        verify(repo, times(1)).deleteById(1L);
        verify(proxy, times(1)).delete(1L);
    }
    @Test
    void testDeleteItem_NotFound_ThrowsException() {
        when(repo.existsById(99L)).thenReturn(false);
        assertThrows(ItemNotFoundException.class, () -> {
            service.deleteItem(99L);
        });
        verify(repo, never()).deleteById(anyLong());
    }
}