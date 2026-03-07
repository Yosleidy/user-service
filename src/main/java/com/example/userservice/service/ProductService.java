package com.example.userservice.service;

import com.example.userservice.entity.Product;
import com.example.userservice.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> getAllProducts() {
        return repository.findAll();
    }

    public Product getProductById(Long id) {
        Optional<Product> product = repository.findById(id);
        return product.orElse(null);
    }

    public Product createProduct(Product product) {
        return repository.save(product);
    }
}