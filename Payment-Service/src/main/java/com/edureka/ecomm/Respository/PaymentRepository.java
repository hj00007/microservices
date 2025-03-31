package com.edureka.ecomm.Respository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edureka.ecomm.entity.Payment;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> getPaymentsByOrderId(Long orderId);
    Optional<Payment> getPaymentsByUserId(Long userId);
}