package com.example.demo.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @GetMapping("/")
    public Mono<String> adminHome() {
        return Mono.just("Welcome to Admin Home Page!");
    }

    @GetMapping("/users")
    public Mono<String> listUsers() {
        return Mono.just("List of all users");
    }

    @GetMapping("/settings")
    public Mono<String> settings() {
        return Mono.just("Admin Settings Page");
    }
}