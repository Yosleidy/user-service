

package com.example.userservice.service;

import com.example.userservice.entity.Product;
import com.example.userservice.repository.ProductRepository;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Test
    void testGetAllProducts() {
        ProductRepository mockRepo = mock(ProductRepository.class);

        Product p1 = new Product();
        p1.setName("Laptop");
        p1.setPrice(1200.0);

        Product p2 = new Product();
        p2.setName("Mouse");
        p2.setPrice(20.0);

        when(mockRepo.findAll()).thenReturn(List.of(p1, p2));

        ProductService service = new ProductService(mockRepo);

        List<Product> products = service.getAllProducts();

        assertEquals(2, products.size());
        assertEquals("Laptop", products.get(0).getName());
        verify(mockRepo, times(1)).findAll();
    }

    @Test
    void testGetProductByIdNotFound() {
        ProductRepository mockRepo = mock(ProductRepository.class);
        when(mockRepo.findById(999L)).thenReturn(Optional.empty());

        ProductService service = new ProductService(mockRepo);

        Product result = service.getProductById(999L);
        assertNull(result);
        verify(mockRepo, times(1)).findById(999L);
    }
}