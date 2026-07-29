package com.example.inventory.controller;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.inventory.dto.InventoryDto;
import com.example.inventory.service.InventoryService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    @Autowired
    InventoryService service;

    @GetMapping("/getdata")
    public List<InventoryDto> getAll(){
        return service.getInventoryRecord();
    }
    @PostMapping("/addrecord")
    public InventoryDto addItem(@Valid @RequestBody InventoryDto item) {
        return service.addInventory(item);
    }
    @PutMapping("/update")
    public InventoryDto update(@Valid @RequestBody InventoryDto item) {
        return service.updateInventory(item);
    }
    @DeleteMapping("/deleterecord/{id}")
    public Optional<InventoryDto> delete(@PathVariable long id) {
        return service.deleteRecord(id);
    }
}