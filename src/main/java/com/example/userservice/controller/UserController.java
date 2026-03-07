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
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto dto) {
        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        User saved = service.createUser(user);

        UserDto response = new UserDto();
        response.setName(saved.getName());
        response.setEmail(saved.getEmail());
        return ResponseEntity.ok(response);
    }
}