package com.example.userservice.controller;

import com.example.userservice.dto.OrderDto;
import com.example.userservice.entity.Orders;
import com.example.userservice.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @GetMapping
    @Operation(summary = "Listar todas las órdenes")
    public ResponseEntity<List<OrderDto>> getOrders() {
        List<OrderDto> orders = service.getAllOrders().stream().map(o -> {
            OrderDto dto = new OrderDto();
            dto.setId(o.getId());
            dto.setUserId(o.getUserId());
            dto.setProductId(o.getProductId());
            dto.setQuantity(o.getQuantity());
            return dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(orders);
    }



    @GetMapping("/{id}")
    @Operation(summary = "Obtener una orden por ID")
    public ResponseEntity<OrderDto> getOrderById(@PathVariable Long id) {
        Orders o = service.getOrderById(id);
        if (o == null) return ResponseEntity.notFound().build();

        OrderDto dto = new OrderDto();
        dto.setId(o.getId());
        dto.setUserId(o.getUserId());
        dto.setProductId(o.getProductId());
        dto.setQuantity(o.getQuantity());
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    @Operation(summary = "Crear una nueva orden")
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto dto) {
        Orders o = new Orders();
        o.setUserId(dto.getUserId());
        o.setProductId(dto.getProductId());
        o.setQuantity(dto.getQuantity());

        Orders saved = service.createOrder(o);

        OrderDto response = new OrderDto();
        response.setId(saved.getId());
        response.setUserId(saved.getUserId());
        response.setProductId(saved.getProductId());
        response.setQuantity(saved.getQuantity());
        return ResponseEntity.ok(response);
    }
}