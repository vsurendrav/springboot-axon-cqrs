package com.boot.axon.cqrs.commands;

import org.axonframework.modelling.command.TargetAggregateIdentifier;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ShipOrderCommand {
	@TargetAggregateIdentifier
    private String orderId;
}
