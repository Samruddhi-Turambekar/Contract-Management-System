package com.example.Contract_Management.controller;

import com.example.Contract_Management.model.User;
import com.example.Contract_Management.service.LoginService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginUser) {

        Optional<User> user = loginService.login(
                loginUser.getEmail(),
                loginUser.getPassword()
        );

        if (user.isPresent()) {

            User loggedInUser = user.get();

            return ResponseEntity.ok(loggedInUser);

        } else {

            return ResponseEntity
                    .status(401)
                    .body("{\"message\":\"Invalid email or password\"}");
        }
    }
}