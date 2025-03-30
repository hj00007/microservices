package com.edureka.paymentservice.orders.entity;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Orders {

	private Long id;

	private Long customer_id;

	private String orderItems;

	private LocalDate orderDate;

	private Double totalAmount;

}
