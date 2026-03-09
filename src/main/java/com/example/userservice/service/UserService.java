package com.example.userservice.service;


import com.example.userservice.entity.User;
import com.example.userservice.repository.UserRepository;

import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User createUser(User user) {
        if (repository.findByEmail(user.getEmail()) != null) {
            throw new RuntimeException("El correo ya está en uso");
        }
        return repository.save(user);
    }
    public User getUserById(Long id) {

        return repository.findById(id).orElse(null);
    }
    public List<User> getUsersByName(String name) {
        return repository.findByNameContainingIgnoreCase(name); // permite búsqueda parcial
    }
    public User updateUser(User user) {
        return repository.save(user); // Si el usuario tiene ID existente, JPA hace update
    }

    // Eliminar usuario por ID
    public void deleteUser(Long id) {
        repository.deleteById(id);
    }
}