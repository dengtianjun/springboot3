package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class HomeController {

    @GetMapping("/home")
    public Mono<String> home() {
        return Mono.just("Welcome to the Home Page! This page is publicly accessible.");
    }

    @GetMapping("/contact")
    public Mono<String> contact() {
        return Mono.just("Contact Page - Also publicly accessible");
    }

    @GetMapping("/user/profile")
    public Mono<String> userProfile() {
        return Mono.just("User Profile Page - Only accessible to authenticated users");
    }
}