package com.example.userservice.service;

import com.example.userservice.entity.Orders;
import com.example.userservice.repository.OrderRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OrderServiceTest {

    @Test
    void testGetAllOrders() {
        OrderRepository mockRepo = mock(OrderRepository.class);
        Orders order = new Orders();
        order.setUserId(1L);
        order.setProductId(1L);

        when(mockRepo.findAll()).thenReturn(List.of(order));

        OrderService service = new OrderService(mockRepo, null, null);

        List<Orders> orders = service.getAllOrders();

        assertEquals(1, orders.size());
        verify(mockRepo, times(1)).findAll();
    }
}