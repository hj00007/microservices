package com.capstone.nirosh.e_commerce.Inventory_Service.controller;

import com.capstone.nirosh.e_commerce.Inventory_Service.entity.Inventory;
import com.capstone.nirosh.e_commerce.Inventory_Service.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    private InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @GetMapping
    public List<Inventory> getAllInventoryItems() {
        return inventoryService.getAllInventoryItems();
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<Inventory> getInventoryByProductId(@PathVariable Long productId) {
        Inventory inventory = inventoryService.getInventoryByProductId(productId);
        return ResponseEntity.ok(inventory);
    }

    @PostMapping("/product/{productId}")
    public ResponseEntity<Inventory> addOrUpdateInventory(@PathVariable Long productId, @RequestParam Integer quantity) {
        Inventory updatedInventory = inventoryService.addOrUpdateInventory(productId, quantity);
        return ResponseEntity.ok(updatedInventory);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteInventory(@PathVariable Long id) {
        inventoryService.deleteInventory(id);
        return ResponseEntity.noContent().build();
    }
}
