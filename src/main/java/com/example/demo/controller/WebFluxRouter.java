package com.example.demo.controller;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class WebFluxRouter {

    @Bean
    public RouterFunction<ServerResponse> routerFunction(WebFluxHandler handler) {
        return route(GET("/router/users/{id}"), handler::getUserById);
    }
}