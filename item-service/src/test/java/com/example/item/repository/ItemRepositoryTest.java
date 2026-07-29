package com.example.item.repository;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
// NEW Spring Boot 4 Import!
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import com.example.item.entity.Item;
@DataJpaTest
public class ItemRepositoryTest {
    @Autowired
    private ItemRepository repo;
    @Test
    void testFindByItemName_Success() {
        Item item = new Item("Laptop", "Gaming Laptop", "Electronics");
        repo.save(item);
        Item found = repo.findByItemName("Laptop");
        assertNotNull(found);
        assertEquals("Electronics", found.getCategory());
    }
    @Test
    void testFindByItemName_NotFound() {
        Item found = repo.findByItemName("NonExistentItem");
        assertNull(found);
    }
}