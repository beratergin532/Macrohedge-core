package com.tradecore.macrohedge_backend.controller;

import com.tradecore.macrohedge_backend.entity.User;
import com.tradecore.macrohedge_backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AuthController {

    private final UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        return userRepository.findByEmail(email)
                .map(user -> ResponseEntity.ok(Map.of("success", true, "user", user)))
                .orElse(ResponseEntity.badRequest().body(Map.of("success", false, "message", "Kullanıcı bulunamadı")));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            return ResponseEntity.badRequest().body(Map.of("success", false, "message", "E-posta zaten kayıtlı"));
        }
        User saved = userRepository.save(user);
        return ResponseEntity.ok(Map.of("success", true, "user", saved));
    }
}