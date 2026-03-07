package com.example.userservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
@Entity
@Getter
@Setter
public class Orders {

    @Id
    @GeneratedValue
    private Long id;

    private Long userId;
    private Long productId;
    private int quantity;

    // Getters y setters
    }
