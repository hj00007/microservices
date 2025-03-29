package com.capstone.nirosh.e_commerce.Order_Processing_Service.repo;


import com.capstone.nirosh.e_commerce.Order_Processing_Service.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    // Custom query methods can be added here if needed
}
