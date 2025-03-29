package com.capstone.nirosh.e_commerce.Payment_Service.Respository;


import com.capstone.nirosh.e_commerce.Payment_Service.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> getPaymentsByOrderId(Long orderId);
    Optional<Payment> getPaymentsByUserId(Long userId);
}