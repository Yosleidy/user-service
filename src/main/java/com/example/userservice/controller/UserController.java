package com.example.userservice.controller;

import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.User;
import com.example.userservice.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getUsers() {
        List<UserDto> users = service.getAllUsers().stream().map(u -> {
            UserDto dto = new UserDto();
            dto.setName(u.getName());
            dto.setEmail(u.getEmail());
            return dto;
        }).collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserDto dto) {
        try {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        User saved = service.createUser(user);

        UserDto response = new UserDto();
        response.setName(saved.getName());
        response.setEmail(saved.getEmail());
        return ResponseEntity.ok(response);
    }catch (RuntimeException e) {
        return ResponseEntity
                .badRequest()
                .body("El usuario ya existe o el correo ya está en uso");
}
    }
    // Buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        User u = service.getUserById(id);
        if (u == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new UserDto(u.getName(), u.getEmail()));
    }

    // Buscar por nombre (exacto o parcial)
    @GetMapping("/search")
    public ResponseEntity<List<UserDto>> getUsersByName(@RequestParam String name) {
        List<UserDto> users = service.getUsersByName(name).stream()
                .map(u -> new UserDto(u.getName(), u.getEmail()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }
}