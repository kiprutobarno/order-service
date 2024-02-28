package com.bookshop.orderservice.repositories;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import com.bookshop.orderservice.models.Order;

public interface OrderRepository extends ReactiveCrudRepository<Order, Long> {

}
