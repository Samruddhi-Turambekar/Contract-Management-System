package com.example.Contract_Management.controller;

import com.example.Contract_Management.model.User;
import com.example.Contract_Management.repository.UserRepository;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/users")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/employees")
    public List<User> getEmployees() {

        return userRepository.findAll()
                .stream()
                .filter(user -> user.getRole() != null)
                .filter(user -> user.getRole().name().equals("EMPLOYEE"))
                .toList();
    }
}