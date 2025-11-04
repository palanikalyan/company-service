package com.example.demo.Service;


import com.example.demo.Entity.User;

import java.util.List;

public interface UserService {
    User signup(User user);
    String login(String username, String password);
    List<User> getAllUsers();
}
