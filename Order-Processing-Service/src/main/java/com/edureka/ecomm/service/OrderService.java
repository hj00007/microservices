package com.edureka.ecomm.service;


import java.util.List;

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

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderITemRepository orderITemRepository;


    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private static final String TOPIC1 = "OrderPlaced";
    @Autowired
    private  KafkaTemplate<String, Object> kafkaTemplate;
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
    	Customer customer = restTemplate.getForObject("http://CUSTOMER-SERVICE/api/v1/customers/" + customerId, Customer.class);
    	Orders orders = new Orders();

    	if(customer!=null) {
    		orders.setCustomer_id(customer.getId());
    	}else {
    		throw  new ResourceNotFoundException("Customer id: "+customerId+" not found");
    	}


    	Product product = restTemplate.getForObject("http://PRODUCT-SERVICE/api/v1/products/" + orderItem.getProduct_id(), Product.class);

    	if(product ==null ) {
    		throw  new ResourceNotFoundException("Product id : "+orderItem.getProduct_id()+" not found");
    	}
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


            Product product = restTemplate.getForObject("http://product-service/products/" + updatedItem.getProduct_id(), Product.class);
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
        sendOrder(orders1);
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
