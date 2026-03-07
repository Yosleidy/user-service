package com.example.userservice.controller;

import com.example.userservice.dto.ProductDto;
import com.example.userservice.entity.Product;
import com.example.userservice.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<ProductDto>> getProducts() {
        List<ProductDto> products = service.getAllProducts().stream().map(p -> {
            ProductDto dto = new ProductDto();
            dto.setId(p.getId());
            dto.setName(p.getName());
            dto.setPrice(p.getPrice());
            return dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id) {
        Product p = service.getProductById(id);
        if (p == null) return ResponseEntity.notFound().build();

        ProductDto dto = new ProductDto();
        dto.setId(p.getId());
        dto.setName(p.getName());
        dto.setPrice(p.getPrice());
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto dto) {
        Product p = new Product();
        p.setName(dto.getName());
        p.setPrice(dto.getPrice());
        Product saved = service.createProduct(p);

        ProductDto response = new ProductDto();
        response.setId(saved.getId());
        response.setName(saved.getName());
        response.setPrice(saved.getPrice());
        return ResponseEntity.ok(response);
    }
}