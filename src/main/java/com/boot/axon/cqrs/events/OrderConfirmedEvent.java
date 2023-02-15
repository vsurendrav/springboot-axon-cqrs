package com.boot.axon.cqrs.events;


import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderConfirmedEvent {
    private String orderId;
}
