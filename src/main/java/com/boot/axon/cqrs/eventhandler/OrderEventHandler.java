package com.boot.axon.cqrs.eventhandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.axonframework.eventhandling.EventHandler;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Service;

import com.boot.axon.cqrs.events.OrderConfirmedEvent;
import com.boot.axon.cqrs.events.OrderCreatedEvent;
import com.boot.axon.cqrs.events.OrderShippedEvent;
import com.boot.axon.cqrs.model.Order;
import com.boot.axon.cqrs.model.OrderStatus;
import com.boot.axon.cqrs.query.FindAllOrderedProductsQuery;
import com.boot.axon.cqrs.repository.OrderRepository;

@Service
public class OrderEventHandler {
	 private OrderRepository orderRepository;
	 
	 @EventHandler
	 public void on(OrderCreatedEvent orderCreatedEvent) {
		 com.boot.axon.cqrs.entity.Order order = new com.boot.axon.cqrs.entity.Order();
		 order.setOrderId(orderCreatedEvent.getOrderId());
		 order.setProductId(orderCreatedEvent.getProductId());
		 order.setStatus(OrderStatus.CREATED.toString());
		 orderRepository.save(order);
	 }
	 
	 @EventHandler
	 public void on(OrderConfirmedEvent orderConfirmedEvent) {
		 Optional<com.boot.axon.cqrs.entity.Order> orderOptional = orderRepository.findById(orderConfirmedEvent.getOrderId());
		 if(orderOptional.isPresent()) {
			 com.boot.axon.cqrs.entity.Order order = orderOptional.get();
			 order.setStatus(OrderStatus.CONFIRMED.toString());
			 orderRepository.save(order);
		 }
	 }
	 
	 @EventHandler
	 public void on(OrderShippedEvent orderShippedEvent) {
		 Optional<com.boot.axon.cqrs.entity.Order> orderOptional = orderRepository.findById(orderShippedEvent.getOrderId());
		 if(orderOptional.isPresent()) {
			 com.boot.axon.cqrs.entity.Order order = orderOptional.get();
			 order.setStatus(OrderStatus.SHIPPED.toString());
			 orderRepository.save(order);
		 }
	 }
	 
	 @QueryHandler
	 public List<Order> handle(FindAllOrderedProductsQuery query) {
		List<com.boot.axon.cqrs.entity.Order> orders = orderRepository.findAll();
		return orders.stream().map(order -> {
			Order orderModel = new Order();
			orderModel.setOrderId(order.getOrderId());
			orderModel.setProductId(order.getProductId());
			orderModel.setStatus(order.getStatus());
			return orderModel;
		}).collect(Collectors.toList());
	 }
}
