package com.edureka.ecommerce.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CustomerNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 7700377567477083439L;
	private final String msg;
}
