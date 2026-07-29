package com.example.item.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.item.entity.Item;
public interface ItemRepository extends JpaRepository<Item, Long>{
    public Item findByItemName(String itemName);
}