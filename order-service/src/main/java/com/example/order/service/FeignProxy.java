package com.example.order.service;
import java.util.List;
import java.util.Optional;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.order.dto.InventoryDto;

@FeignClient(name="INVENTORY-SERVICE")
public interface FeignProxy {
    @PutMapping("/api/inventory/update")
    public InventoryDto update(@RequestBody InventoryDto item);
    @GetMapping("/api/inventory/getdata")
    public List<InventoryDto> getAll();
}