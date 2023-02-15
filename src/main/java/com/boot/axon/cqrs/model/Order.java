package com.boot.axon.cqrs.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Order {
	private String orderId;
    private String productId;
    private String status;

}
