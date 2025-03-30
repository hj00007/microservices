package com.edureka.ecomm.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Setter
@Getter
@ToString
public class Orders {


    private Long id;


    private Long customer_id;

    private String orderItems;

    private LocalDate orderDate;

    private Double totalAmount;



}
