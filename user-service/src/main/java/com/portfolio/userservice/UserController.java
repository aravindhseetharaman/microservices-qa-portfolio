package com.portfolio.userservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(List.of(
            new User(1L, "John Doe", "john@example.com", "+44123456789"),
            new User(2L, "Jane Smith", "jane@example.com", "+44987654321"),
            new User(3L, "Bob Wilson", "bob@example.com", "+44555555555")
        ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable Long id) {
        return ResponseEntity.ok(
            new User(id, "John Doe", "john@example.com", "+44123456789")
        );
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        user.setId(System.currentTimeMillis());
        return ResponseEntity.status(201).body(user);
    }
}
