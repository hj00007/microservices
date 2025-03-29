package com.capstone.nirosh.e_commerce.Order_Processing_Service.dao;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;


@Setter
@Getter
@ToString
public class Product {

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
