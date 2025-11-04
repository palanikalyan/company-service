package com.example.demo.Service;

import java.util.List;

import com.example.demo.Entity.User;

public interface UserService {
    User signup(User user);
    String login(String username, String password);
    List<User> getAllUsers();

    // 🔹 Added for Admin use
    void deleteUser(Long id);
}
