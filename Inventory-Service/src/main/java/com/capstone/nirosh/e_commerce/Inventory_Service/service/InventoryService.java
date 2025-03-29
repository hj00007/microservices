package com.capstone.nirosh.e_commerce.Inventory_Service.service;


import com.capstone.nirosh.e_commerce.Inventory_Service.ExceptionHandling.ResourceNotFoundException;
import com.capstone.nirosh.e_commerce.Inventory_Service.entity.Inventory;
import com.capstone.nirosh.e_commerce.Inventory_Service.repo.InventoryRepository;
import com.capstone.nirosh.e_commerce.Order_Processing_Service.entity.OrderItem;
import com.capstone.nirosh.e_commerce.Order_Processing_Service.entity.Product;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class InventoryService {

    public Logger logger = LogManager.getLogger(InventoryService.class);
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private KafkaTemplate kafkaTemplate;

    public final String TOPIC = "InventoryUpdated";

    public List<Inventory> getAllInventoryItems() {
        return inventoryRepository.findAll();
    }

    public Inventory getInventoryByProductId(Long productId) {
        return inventoryRepository.findByProductId(productId);
    }

    @Transactional
    public Inventory addOrUpdateInventory(Long productId, Integer quantity) {
        Product product = restTemplate.getForObject("http://product-container:8090/products/" + productId, Product.class);

        //Product product = productService.getProductById(productId);
        Inventory inventory = inventoryRepository.findByProductId(productId);

        if (inventory == null) {
            if (product != null) {
                inventory = new Inventory();
                inventory.setProductId(product.getId());
            } else {
                throw new ResourceNotFoundException("Product id :" + productId + " not found");
            }
        } else {
            throw new ResourceNotFoundException("Inventory  for product id :" + productId + " not found");
        }
        inventory.setQuantity(quantity);
        Inventory inventory1= inventoryRepository.save(inventory);
        logger.info("Inventory service: inventory updated event triggered inventory id :"+inventory1.getId());
        sendOrderItems(inventory1);
        return inventory1;
    }

    @Transactional
    public void deleteInventory(Long id) {
        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Inventory item not found"));
        inventoryRepository.delete(inventory);
    }

    @KafkaListener(topics = "OrderPlaced", groupId = "order_group")
    public void consumeOrder(OrderItem orderItem) {
            logger.info("Consumed order items data product ID:" + orderItem.getProduct_id() + " ,quantity: " + orderItem.getQuantity());
            addOrUpdateInventory(orderItem.getProduct_id(), orderItem.getQuantity());

    }
    public void sendOrderItems(Object message) {
        kafkaTemplate.send(TOPIC, message);
    }

}
