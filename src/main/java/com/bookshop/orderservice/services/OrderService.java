package com.bookshop.orderservice.services;

import org.springframework.stereotype.Service;

import com.bookshop.orderservice.dtos.Book;
import com.bookshop.orderservice.models.Order;
import com.bookshop.orderservice.models.OrderStatus;
import com.bookshop.orderservice.repositories.OrderRepository;
import com.bookshop.orderservice.webClients.BookClient;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class OrderService {
    private final BookClient bookClient;
    private final OrderRepository orderRepository;

    public OrderService(BookClient bookClient, OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        this.bookClient = bookClient;
    }

    public Flux<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public Mono<Order> submitOrder(String isbn, int quantity) {
        return bookClient.getBookByIsbn(isbn).map(book -> buildAcceptedOrder(book, quantity)).defaultIfEmpty(
                buildRejectedOrder(isbn, quantity)).flatMap(orderRepository::save);
    }

    public static Order buildRejectedOrder(String isbn, int quantity) {
        return Order.of(isbn, null, null, quantity, OrderStatus.REJECTED);
    }

    public static Order buildAcceptedOrder(Book book, int quantity) {
        return Order.of(book.isbn(), book.title() + " - " + book.author(), book.price(), quantity,
                OrderStatus.ACCEPTED);
    }

}
