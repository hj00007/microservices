package com.capstone.nirosh.e_commerce.Product_Catalog_Service.repository;

import com.capstone.nirosh.e_commerce.Product_Catalog_Service.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(String category);
}
