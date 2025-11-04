package com.example.demo.Controller;


import com.example.demo.Entity.User;
import com.example.demo.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping("/signup")
    public ResponseEntity<User> signup(@RequestBody User user) {
        return ResponseEntity.ok(service.signup(user));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestParam String username, @RequestParam String password) {
        return ResponseEntity.ok(service.login(username, password));
    }

    // ✅ New endpoint - get all users
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(service.getAllUsers());
    }
}
