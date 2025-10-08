package com.example.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/messages")
public class MessageController {

    @GetMapping("/public")
    public Mono<Map<String, Object>> getPublicMessage() {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "This is a public message");
        response.put("timestamp", LocalDateTime.now());
        return Mono.just(response);
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public Mono<Map<String, Object>> getUserMessage(Principal principal) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "This is a user-only message");
        response.put("user", principal.getName());
        response.put("timestamp", LocalDateTime.now());
        return Mono.just(response);
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<Map<String, Object>> getAdminMessage(Principal principal) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", "This is an admin-only message");
        response.put("admin", principal.getName());
        response.put("timestamp", LocalDateTime.now());
        return Mono.just(response);
    }

    @PostMapping("/send")
    @PreAuthorize("hasRole('USER')")
    public Mono<Map<String, Object>> sendMessage(@RequestBody Map<String, String> payload, Principal principal) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "Message sent successfully");
        response.put("from", principal.getName());
        response.put("message", payload.get("message"));
        response.put("timestamp", LocalDateTime.now());
        return Mono.just(response);
    }

    @PostMapping("/secure-send")
    @PreAuthorize("hasRole('ADMIN')")
    public Mono<Map<String, Object>> sendSecureMessage(@RequestBody Map<String, String> payload, Principal principal) {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "Secure message sent successfully");
        response.put("from", principal.getName());
        response.put("message", "[SECURE] " + payload.get("message"));
        response.put("timestamp", LocalDateTime.now());
        return Mono.just(response);
    }
}