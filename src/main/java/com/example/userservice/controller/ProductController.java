package com.example.userservice.controller;

import com.example.userservice.dto.ProductDto;
import com.example.userservice.entity.Product;
import com.example.userservice.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.parameters.RequestBody;

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
    @Operation(summary = "Listar todos los productos")
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
    @Operation(summary = "Obtener un producto por ID")
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
    @Operation(summary = "Crear un nuevo producto")
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
    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un producto existente")
    public ResponseEntity<Product> updateProduct(
            @PathVariable Long id,
            @RequestBody(description = "Datos actualizados del producto") @org.springframework.web.bind.annotation.RequestBody ProductDto dto)           {

        Product product = service.getProductById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        product.setName(dto.getName());
        product.setPrice(dto.getPrice());

        Product updated = service.updateProduct(product);
        return ResponseEntity.ok(updated);
    }
    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un producto por ID")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        Product product = service.getProductById(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }

        service.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}