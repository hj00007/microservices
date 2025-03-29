package com.capstone.nirosh.e_commerce.Payment_Service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@Setter
@Getter
@ToString
@Entity
public class Payment implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;


    private Long orderId;
    
    private Double amount;
    
    private String paymentMethod;

    private String status;
    
    private LocalDateTime paymentDate;

    public Payment(){}

}
