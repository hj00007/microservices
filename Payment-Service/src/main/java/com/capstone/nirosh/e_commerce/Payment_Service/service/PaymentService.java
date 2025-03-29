package com.capstone.nirosh.e_commerce.Payment_Service.service;


import com.capstone.nirosh.e_commerce.Order_Processing_Service.entity.entity.OrderItem;
import com.capstone.nirosh.e_commerce.Order_Processing_Service.entity.entity.Orders;
import com.capstone.nirosh.e_commerce.Payment_Service.Respository.PaymentRepository;
import com.capstone.nirosh.e_commerce.Payment_Service.entity.Payment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;


@Service
public class PaymentService {

    private Logger logger= Logger.getLogger(String.valueOf(PaymentService.class));

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Transactional
    public List<Payment> getAllPayments(){
        return paymentRepository.findAll();
    }
@Transactional
public Payment getPayments(Long paymentId){
    Optional<Payment> payment= paymentRepository.findById(paymentId);
        return payment.orElse(null);


}
    @Transactional
    public Payment getPaymentByOrder(Long orderId){
        Optional<Payment> payment=paymentRepository.getPaymentsByOrderId(orderId);
        return payment.orElse(null);
    }

   /* @RabbitListener(queues = RabbitMQConfig.ORDER_QUEUE)
    public void handleOrder(Orders order) {
        // Process the payment for the order
        logger.info("Received order: " + order);
        // Logic for processing the payment
        processPayment(order.getId(), order.getProductPrice(),order.getPaymentMethod());
    }*/
    @Transactional
    public Payment processPayment(Long orderId, Double amount, String paymentMethod) {
        logger.info("Validating received order...");
        // 1. Retrieve the order details
        Orders orderResponse = restTemplate.getForObject("http://order-container:8092/orders/"+orderId, Orders.class);
        if(orderResponse!=null) {
            // 2. Initiate the payment
            logger.info("Order is validated success..");
            Payment payment = new Payment();
            payment.setOrderId(orderResponse.getId());
            payment.setAmount(amount);
            payment.setPaymentMethod(paymentMethod);
            payment.setPaymentDate(LocalDateTime.now());

            // Save initial payment state
            //payment = paymentRepository.save(payment);

            payment.setStatus("SUCCESS");

            // 4. Save final payment state
            Payment payment1= paymentRepository.save(payment);
            logger.info("Payment made success for order: " + orderResponse.getId());
          //  rabbitTemplate.convertAndSend(paymentExchange, paymenRoutineKey, payment1);
            return payment1;
        }
           return null;
    }

    @KafkaListener(topics = "OrderPlaced", groupId = "order_group")
    public void consumeOrder(OrderItem orderItem) {
        logger.info("Consumed order items data product ID:" + orderItem.getProduct_id() + " ,quantity: " + orderItem.getQuantity());
        processPayment(orderItem.getId(), orderItem.getPrice(),"CREDIT");

    }
}
