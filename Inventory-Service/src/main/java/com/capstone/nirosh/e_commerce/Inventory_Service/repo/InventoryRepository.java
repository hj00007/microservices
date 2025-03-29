package com.capstone.nirosh.e_commerce.Inventory_Service.repo;

import com.capstone.nirosh.e_commerce.Inventory_Service.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Inventory findByProductId(Long productId);
}
