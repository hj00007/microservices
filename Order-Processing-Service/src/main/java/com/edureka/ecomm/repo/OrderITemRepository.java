package com.edureka.ecomm.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edureka.ecomm.entity.OrderItem;

@Repository
public interface OrderITemRepository extends JpaRepository<OrderItem, Long> {
    // Custom query methods can be added here if needed
}
