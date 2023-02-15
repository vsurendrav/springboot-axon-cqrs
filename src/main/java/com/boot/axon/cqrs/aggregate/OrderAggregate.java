package com.boot.axon.cqrs.aggregate;

import org.axonframework.commandhandling.CommandHandler;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.modelling.command.AggregateIdentifier;
import org.axonframework.modelling.command.AggregateLifecycle;
import org.axonframework.spring.stereotype.Aggregate;

import com.boot.axon.cqrs.commands.ConfirmOrderCommand;
import com.boot.axon.cqrs.commands.CreateOrderCommand;
import com.boot.axon.cqrs.commands.ShipOrderCommand;
import com.boot.axon.cqrs.events.OrderConfirmedEvent;
import com.boot.axon.cqrs.events.OrderCreatedEvent;
import com.boot.axon.cqrs.events.OrderShippedEvent;

import lombok.NoArgsConstructor;

@Aggregate
@NoArgsConstructor
public class OrderAggregate {
	
	@AggregateIdentifier
	private String orderId;
	private boolean orderConfirmed;
	
	@CommandHandler
	public OrderAggregate(CreateOrderCommand createOrderCommand) {
		AggregateLifecycle.apply(new OrderCreatedEvent(createOrderCommand.getOrderId(), createOrderCommand.getProductId()));
	}
	
	@CommandHandler
	public void handle(ConfirmOrderCommand confirmOrderCommand) {
		if(orderConfirmed) {
		  return;
		}
		AggregateLifecycle.apply(new OrderConfirmedEvent(orderId));
	}
	
	@CommandHandler
	public void handle(ShipOrderCommand shipOrderCommand) throws Exception {
		if(!orderConfirmed) {
			throw new Exception();
		}
		AggregateLifecycle.apply(new OrderShippedEvent(orderId));
	}
	
	@EventSourcingHandler
	public void on(OrderCreatedEvent orderCreatedEvent) {
		this.orderId = orderCreatedEvent.getOrderId();
		orderConfirmed = false;
	}
	
	@EventSourcingHandler
	public void on(OrderConfirmedEvent orderConfirmedEvent) {
		orderConfirmed = true;
	}
	
	

}
