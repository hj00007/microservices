package com.capstone.nirosh.e_commerce.Product_Catalog_Service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@ToString
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Double unitPrice;

    private String category;


    public Product() {}

    public Product(String name, String description, Double unitPrice, String category) {
        this.name = name;
        this.description = description;
        this.unitPrice = unitPrice;
        this.category = category;
    }

}
