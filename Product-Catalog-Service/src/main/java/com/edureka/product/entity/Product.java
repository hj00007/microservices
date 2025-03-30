package com.edureka.product.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Setter
@Getter
@ToString
@Table(name = "products")
@SequenceGenerator(name = "productid", sequenceName = "productid", allocationSize = 1)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "productid")
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
