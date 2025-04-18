package com.edureka.ecomm.dao;

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
public class OrderItem {

	private Long id;

	private Long product_id;

	private Integer quantity;

	private Double price;

}
