package com.example.PlatePilotBack.controller;

import com.example.PlatePilotBack.repository.userRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class authController {

    @Autowired
    private userRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> loginRequest) {
        String username = loginRequest.get("username");
        String password = loginRequest.get("password");

        return userRepository.findById(username)
                .map(user -> passwordEncoder.matches(password, user.getPasswordHash())
                        ? ResponseEntity.ok("Login successful")
                        : ResponseEntity.status(401).body("Invalid credentials"))
                .orElse(ResponseEntity.status(404).body("User not found"));
    }
}
