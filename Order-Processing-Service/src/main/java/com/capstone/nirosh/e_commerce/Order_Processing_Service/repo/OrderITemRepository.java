package com.capstone.nirosh.e_commerce.Order_Processing_Service.repo;


import com.capstone.nirosh.e_commerce.Order_Processing_Service.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderITemRepository extends JpaRepository<OrderItem, Long> {
    // Custom query methods can be added here if needed
}
