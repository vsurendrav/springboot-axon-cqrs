package com.boot.axon.cqrs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.boot.axon.cqrs.entity.Order;

import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, String> {

}
