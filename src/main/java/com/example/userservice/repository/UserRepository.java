package com.example.userservice.repository;
// Esto te da acceso a operaciones CRUD sin escribir código extra.
import com.example.userservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}