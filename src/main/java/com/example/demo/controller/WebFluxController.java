package com.example.demo.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
public class WebFluxController {

    @GetMapping("/flux")
    public Flux<String> getFlux() {
        return Flux.just("Hello", "WebFlux", "World")
                .delayElements(Duration.ofMillis(500));
    }

    @GetMapping("/mono")
    public Mono<String> getMono() {
        return Mono.just("Hello WebFlux World!");
    }
}