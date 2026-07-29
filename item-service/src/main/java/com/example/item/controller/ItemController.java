package com.example.item.controller;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.item.dto.ItemDto;
import com.example.item.dto.ItemListDto;
import com.example.item.entity.Item;
import com.example.item.service.ItemService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/item")
public class ItemController {
    @Autowired
    ItemService service;

    @GetMapping("/getall")
    public List<ItemListDto> itemList(){
        return service.getAllItems();
    }
    @PostMapping("/additem")
    public ItemDto itemCreation(@Valid @RequestBody ItemDto dto) {
        return service.addItem(dto);
    }
    @DeleteMapping("/delete/{id}")
    //@PreAuthorize("hasAuthority('ADMIN')")
    public String deleteItem(@PathVariable Long id) {
        return service.deleteItem(id);
    }
}