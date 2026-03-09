package com.example.userservice.controller;

import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.User;
import com.example.userservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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
    @Operation(summary = "Listar todos los usuarios")
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
    @Operation(summary = "Crear un nuevo usuario")
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
    @Operation(summary = "Obtener un usuario por ID")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        User u = service.getUserById(id);
        if (u == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new UserDto(u.getName(), u.getEmail()));
    }

    // Buscar por nombre (exacto o parcial)
    @GetMapping("/search")
    @Operation(summary = "Obtener un usuario por el nombre")
    public ResponseEntity<List<UserDto>> getUsersByName(@RequestParam String name) {
        List<UserDto> users = service.getUsersByName(name).stream()
                .map(u -> new UserDto(u.getName(), u.getEmail()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un usuario existente")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable Long id,
            @RequestBody(description = "Datos actualizados del usuario") @org.springframework.web.bind.annotation.RequestBody UserDto dto)
            {

        User user = service.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());

        User updated = service.updateUser(user);
        return ResponseEntity.ok(new UserDto(updated.getName(), updated.getEmail()));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un usuario por ID")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        User user = service.getUserById(id);
        if (user == null) {
            return ResponseEntity.notFound().build();
        }

        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}