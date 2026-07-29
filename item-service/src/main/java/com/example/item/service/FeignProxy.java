package com.example.item.service;
import java.util.List;
import java.util.Optional;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.item.dto.InventoryDto;

@FeignClient(name="INVENTORY-SERVICE")
public interface FeignProxy {
    @DeleteMapping("/api/inventory/deleterecord/{id}")
    public Optional<InventoryDto> delete(@PathVariable long id);
    @PostMapping("/api/inventory/addrecord")
    public InventoryDto addItem(@RequestBody InventoryDto dto);
}