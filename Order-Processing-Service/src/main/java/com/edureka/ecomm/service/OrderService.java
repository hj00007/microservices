package com.edureka.ecomm.service;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import com.edureka.ecomm.ExceptionHandling.ResourceNotFoundException;
import com.edureka.ecomm.dao.Customer;
import com.edureka.ecomm.dao.Product;
import com.edureka.ecomm.entity.OrderItem;
import com.edureka.ecomm.entity.Orders;
import com.edureka.ecomm.repo.OrderITemRepository;
import com.edureka.ecomm.repo.OrderRepository;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderITemRepository orderITemRepository;


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private  KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC1 = "OrderPlaced";
    private static final String TOPIC2 = "OrderItemsPlaced";
    public List<Orders> getAllOrders() {
        return orderRepository.findAll();
    }
   public Logger logger = LogManager.getLogger(OrderService.class);
    public Orders getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Transactional
    public Orders createOrder(String customerId, OrderItem orderItem) {
//    	Customer customer = restTemplate.getForObject("http://customer-container:8091/customers/" + customerId, Customer.class);
    	Orders orders = new Orders();
//
//    	if(customer!=null) {
//    		orders.setCustomer_id(customer.getId());
//    	}else {
//    		throw  new ResourceNotFoundException("Customer id: "+customerId+" not found");
//    	}
//
//    	double totalAmount = 0.0;
//
//    	Product product = restTemplate.getForObject("http://product-container:8090/products/" + orderItem.getProduct_id(), Product.class);
//
//    	if(product!=null) {
//    		orderItem.setPrice(product.getUnitPrice());
//    		totalAmount += orderItem.getQuantity() * product.getUnitPrice();
//    	}else {
//    		throw  new ResourceNotFoundException("Product id : "+orderItem.getProduct_id()+" not found");
//    	}
    	OrderItem orderItem1=orderITemRepository.save(orderItem);
    	orders.setOrderItems(orderItem1.toString());
    	orders.setTotalAmount(orderItem.getPrice() * orderItem .getQuantity());
    	Orders orders1= orderRepository.save(orders);
    	sendOrder(orders1);
    	sendOrderItems(orderItem1);
    	logger.info("Order written into the topics {"+TOPIC1+","+TOPIC2+"}");
    	return orders1;
    }

    @Transactional
    public Orders updateOrder(Long id, OrderItem updatedItem) {
        Orders order = getOrderById(id);
        double totalAmount = 0.0;


            Product product = restTemplate.getForObject("http://product-container:8090/products/" + updatedItem.getProduct_id(), Product.class);
            if(product!=null) {
                updatedItem.setPrice(product.getUnitPrice());
                totalAmount += updatedItem.getQuantity() * product.getUnitPrice();
            }else {
                throw  new ResourceNotFoundException("Product id : "+updatedItem.getProduct_id()+" not found");
            }

        OrderItem orderItem1=orderITemRepository.save(updatedItem);
        order.setOrderItems(orderItem1.toString());
        order.setTotalAmount(totalAmount);
        Orders orders1= orderRepository.save(order);
        //sendOrder(orders1);
        sendOrderItems(orderItem1);
        logger.info("Order written into the topics {"+TOPIC1+","+TOPIC2+"}");
        return orders1;
    }

    @Transactional
    public void deleteOrder(Long id) {
        Orders order = getOrderById(id);
        orderRepository.delete(order);
    }


    public void sendOrder(Object message) {
        kafkaTemplate.send(TOPIC1, message);
    }
    public void sendOrderItems(Object message) {
        kafkaTemplate.send(TOPIC2, message);
    }

}
