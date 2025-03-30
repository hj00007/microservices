package com.edureka.ecomm.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.edureka.ecomm.entity.Orders;

@Repository
public interface OrderRepository extends JpaRepository<Orders, Long> {
    // Custom query methods can be added here if needed
}
