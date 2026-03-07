package com.example.userservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderDto {
    private Long id;
    private Long userId;
    private Long productId;
    private int quantity;
}