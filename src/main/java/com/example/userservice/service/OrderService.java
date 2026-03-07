package com.example.userservice.service;

import com.example.userservice.entity.Orders;
import com.example.userservice.repository.OrderRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository repository;

    public OrderService(OrderRepository repository) {
        this.repository = repository;
    }

    public List<Orders> getAllOrders() {
        return repository.findAll();
    }

    public Orders getOrderById(Long id) {
        Optional<Orders> order = repository.findById(id);
        return order.orElse(null);
    }

    public Orders createOrder(Orders order) {
        return repository.save(order);
    }
}