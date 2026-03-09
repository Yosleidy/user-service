
package com.example.userservice.controller;

import com.example.userservice.dto.UserDto;
import com.example.userservice.entity.User;
import com.example.userservice.service.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    @Test
    void testGetUsers() {
        // Creamos un usuario de prueba
        User user = new User();
        user.setName("Jorge");
        user.setEmail("jorge@gmail.com");

        // Mock del servicio
        UserService mockService = mock(UserService.class);
        when(mockService.getAllUsers()).thenReturn(List.of(user));

        // Instanciamos el controlador con el mock
        UserController controller = new UserController(mockService);

        // Ejecutamos el endpoint
        ResponseEntity<List<UserDto>> response = controller.getUsers();

        // Verificaciones
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        assertEquals("Jorge", response.getBody().get(0).getName());
    }
}