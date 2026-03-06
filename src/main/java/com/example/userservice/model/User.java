package com.example.userservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data //Lombok genera getters, setters, toString, equals, hashCode
@Entity //marca la clase como entidad JPA
public class User {

    @Id //llave primaria auto-generada
    @GeneratedValue //llave primaria auto-generada
    private Long id;

    private String name;
    private String email;
}