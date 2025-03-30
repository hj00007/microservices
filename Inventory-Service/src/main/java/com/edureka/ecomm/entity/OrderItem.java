package com.edureka.ecomm.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class OrderItem {


    private Long id;

    private Long product_id;

    private Integer quantity;

    private Double price;

}
