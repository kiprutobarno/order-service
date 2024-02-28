package com.bookshop.orderservice.models;

import java.time.Instant;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

@Table("orders")
public record Order(
        @Id Long id,
        String isbn,
        Double price,
        Integer quantity,
        OrderStatus status,
        @CreatedDate Instant createdDate,
        @LastModifiedDate Instant lastModifiedDate,
        @Version int version) {
    public static Order of(String isbn, String name, Double price, Integer quantity, OrderStatus status) {
        return new Order(null, isbn, price, quantity, status, null, null, 0);
    }

}