package com.edureka.ecomm.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.edureka.ecomm.ExceptionHandling.ResourceNotFoundException;
import com.edureka.ecomm.entity.Inventory;
import com.edureka.ecomm.entity.OrderItem;
import com.edureka.ecomm.entity.Product;
import com.edureka.ecomm.repo.InventoryRepository;

@Service
@SuppressWarnings("rawtypes")
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
		Product product = null;
		//TODO after product service
		product = restTemplate.getForObject("http://PRODUCT-SERVICE/api/v1/products/" + productId, Product.class);
		if (product == null) {
			throw new ResourceNotFoundException("Product id :" + productId + " not found");
		}

		Inventory inventory = inventoryRepository.findByProductId(productId);

		if (inventory == null) {
			//if (product != null) {
				inventory = new Inventory();
				inventory.setProductId(productId);
			//}
		}
		inventory.setQuantity(quantity);
		Inventory inventory1 = inventoryRepository.save(inventory);
		return inventory1;
	}

	@Transactional
	public void deleteInventory(Long id) {
		Inventory inventory = inventoryRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Inventory item not found"));
		inventoryRepository.delete(inventory);
	}

	@KafkaListener(topics = "OrderItemsPlaced", groupId = "inventory_group")
	public void consumeOrder(OrderItem orderItem) {
		logger.info("Consumed order items data product ID:" + orderItem.getProduct_id() + " ,quantity: "
				+ orderItem.getQuantity());
		addOrUpdateInventoryForOrder(orderItem.getProduct_id(), orderItem.getQuantity());

	}

	@SuppressWarnings("unchecked")
	public void sendOrderItems(Object message) {
		kafkaTemplate.send(TOPIC, message);
	}
	
	@Transactional
    public void addOrUpdateInventoryForOrder(Long productId, Integer quantity) {
        Product product = restTemplate.getForObject("http://PRODUCT-SERVICE/api/v1/products/" + productId, Product.class);
        if(product == null ) {
        	//TODO: handle failure, send to order failure topic
        }
        //Product product = productService.getProductById(productId);
        Inventory inventory = inventoryRepository.findByProductId(productId);
        int updatedQuatity = 0;
        if (inventory != null) {
           updatedQuatity = inventory.getQuantity() - quantity;
           if(updatedQuatity <0) {
        	   //TODO handle failure, send to order failure topic
           }
        } else {
        	//TODO: handle failure, send to order failure topic
            //throw new ResourceNotFoundException("Inventory  for product id :" + productId + " not found");
        }
        inventory.setQuantity(updatedQuatity);
        Inventory inventory1= inventoryRepository.save(inventory);
        logger.info("Inventory service: inventory updated event triggered inventory id :"+inventory1.getId());
        sendOrderItems(inventory1);
    }

}
