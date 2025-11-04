package com.example.demo.Service;

import com.example.demo.Entity.User;
import com.example.demo.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;

    @Override
    public User signup(User user) {
        if (repository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        return repository.save(user);
    }

    @Override
    public String login(String username, String password) {
        User user = repository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!user.getPassword().equals(password)) {
            throw new RuntimeException("Invalid password");
        }
        return "Login successful";
    }

    @Override
    public List<User> getAllUsers() {
        return repository.findAll();
    }
}
