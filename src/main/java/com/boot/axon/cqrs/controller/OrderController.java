package com.boot.axon.cqrs.controller;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.axonframework.commandhandling.gateway.CommandGateway;
import org.axonframework.messaging.responsetypes.ResponseTypes;
import org.axonframework.queryhandling.QueryGateway;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boot.axon.cqrs.commands.ConfirmOrderCommand;
import com.boot.axon.cqrs.commands.CreateOrderCommand;
import com.boot.axon.cqrs.commands.ShipOrderCommand;
import com.boot.axon.cqrs.model.Order;
import com.boot.axon.cqrs.query.FindAllOrderedProductsQuery;

@RestController
@RequestMapping("/api/order")
public class OrderController {
	private CommandGateway commandGateway;
	private QueryGateway queryGateway;
	
	@PostMapping
	public CompletableFuture<Void> shipOrder() {
		String orderId = UUID.randomUUID().toString();
		
		  return commandGateway.send(new CreateOrderCommand(orderId, "deluxe chair"))
				  .thenCompose(result -> commandGateway.send(new ConfirmOrderCommand(orderId)))
				  .thenCompose(result -> commandGateway.send(new ShipOrderCommand(orderId)));
	}
	
	@GetMapping("/all-orders")
	public CompletableFuture<List<Order>> findAllOrders() {
	    return queryGateway.query(new FindAllOrderedProductsQuery(), ResponseTypes.multipleInstancesOf(Order.class));
	}
}
