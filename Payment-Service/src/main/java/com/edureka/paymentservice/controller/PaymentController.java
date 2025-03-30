package com.edureka.paymentservice.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.edureka.paymentservice.OrderNotFoundException.ResourceNotFoundException;
import com.edureka.paymentservice.entity.Payment;
import com.edureka.paymentservice.service.PaymentService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/{id}")
    public Payment getPayment(@PathVariable Long id){
        Payment payment= paymentService.getPayments(id);
        if(payment != null){
            return payment;
        }
        throw new ResourceNotFoundException("Payment not found id: "+id);

    }

    @GetMapping("/order/{id}")
    public Payment getPaymentByOrderId(@PathVariable Long id){
        return paymentService.getPaymentByOrder(id);
    }


    @PostMapping
    public ResponseEntity<String> makePayment(@RequestBody Payment paymentRequest) {
        Payment payment = paymentService.processPayment(
                paymentRequest.getOrderId(), 
                paymentRequest.getAmount(), 
                paymentRequest.getPaymentMethod());
        if(payment!=null){
            return ResponseEntity.ok("Payment made success and shipment will be dispatched soon");
        }
        throw new ResourceNotFoundException("Order not found to process the payment id: "+paymentRequest.getOrderId());

    }

    @GetMapping()
    public List<Payment> paymentList(){
        return paymentService.getAllPayments();
    }
}
