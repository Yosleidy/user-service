package com.example.userservice.service;
import com.example.userservice.entity.Product;
import org.springframework.stereotype.Service;
import com.example.userservice.repository.OrderRepository;
import com.example.userservice.entity.Orders;
import com.example.userservice.entity.User;


import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final UserService userService;       // servicio interno
    private final ProductService productService; // servicio interno

    public OrderService(OrderRepository repository,
                        UserService userService,
                        ProductService productService) {
        this.repository = repository;
        this.userService = userService;
        this.productService = productService;
    }

    public Orders createOrder(Orders order) {
        User user = userService.getUserById(order.getUserId());
        Product product = productService.getProductById(order.getProductId());

        if (user == null || product == null) {
            throw new RuntimeException("User or Product not found");
        }

        return repository.save(order);
    }
    public List<Orders> getAllOrders() {
        return repository.findAll();
    }
    public Orders getOrderById(Long id) {
        Optional<Orders> orders= repository.findById(id);
        return orders.orElse(null);
    }
}