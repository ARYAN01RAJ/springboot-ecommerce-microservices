package com.example.inventory.repository;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
// Spring Boot 4 Import
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import com.example.inventory.entity.Inventory;
@DataJpaTest
public class InventoryRepositoryTest {
    @Autowired
    private InventoryRepository repo;
    @Test
    void testFindByItemId_Success() {
        Inventory inventory = new Inventory(55L, 200, "Warehouse North");
        repo.save(inventory);
        Inventory found = repo.findByItemId(55L);
        assertNotNull(found);
        assertEquals(200, found.getQuantity());
        assertEquals("Warehouse North", found.getWarehouseLocation());
    }
    @Test
    void testFindByItemId_NotFound() {
        Inventory found = repo.findByItemId(999L);
        assertNull(found);
    }
}