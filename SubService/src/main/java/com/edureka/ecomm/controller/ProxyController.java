package com.edureka.ecomm.controller;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.edureka.ecomm.dao.Inventory;

@RestController
@RequestMapping("/api/v1/proxy")
public class ProxyController {

	// change to Redis
    private final Map<String, Boolean> stockCache = new ConcurrentHashMap<>();
   
    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/checkInventory")
    public ResponseEntity<Boolean> checkStock(@RequestParam String productId, @RequestParam int quantity) {
        // Check cache first
        if (stockCache.containsKey(productId)) {
            System.out.println("Proxy Service: Cache hit for product " + productId);
            return ResponseEntity.ok(stockCache.get(productId));
        }

        // Call Inventory Service and cache the result
        String inventoryUrl = "http://INVENTORY-SERVICE/api/v1/inventory/productId/" + productId;
        Inventory invntry = restTemplate.getForObject(inventoryUrl, Inventory.class);
        boolean isAvailable = invntry!= null ? true : false;
        System.out.println("Proxy Service: Cache miss. Updating cache for product " + productId);
        stockCache.put(productId, isAvailable);

        return ResponseEntity.ok(isAvailable);
    }
}
