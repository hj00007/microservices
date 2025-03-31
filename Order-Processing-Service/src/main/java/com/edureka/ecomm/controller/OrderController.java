package com.edureka.ecomm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.edureka.ecomm.entity.OrderItem;
import com.edureka.ecomm.entity.Orders;
import com.edureka.ecomm.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping
    public List<Orders> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Orders> getOrderById(@PathVariable Long id) {
        Orders order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @PostMapping("/customer/{customerId}")
    public ResponseEntity<Orders> placeOrder(@PathVariable String customerId, @RequestBody OrderItem orderItems) {
        Orders createdOrder = orderService.createOrder(customerId, orderItems);
        return ResponseEntity.ok(createdOrder);
    }
    
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Orders>> getOrderByCustomerId(@PathVariable String customerId) {
        List<Orders> createdOrder = orderService.getOrderByCustomerId(customerId);
        return ResponseEntity.ok(createdOrder);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Orders> updateOrder(@PathVariable Long id, @RequestBody OrderItem updatedItem) {
        Orders updatedOrder = orderService.updateOrder(id, updatedItem);
        return ResponseEntity.ok(updatedOrder);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }
}
