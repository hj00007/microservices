package com.capstone.nirosh.e_commerce.Order_Processing_Service.entity.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Product {

    private Long id;

    private String name;

    private String description;

    private Double unitPrice;

    private String category;


}
