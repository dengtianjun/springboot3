package com.example.demo.controller;

import com.example.demo.controller.BlogHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class BlogRouter {
    
    @Bean
    public RouterFunction<ServerResponse> blogRoutes(BlogHandler blogHandler) {
        return route(GET("/blog"), blogHandler::listBlogPosts)
                .andRoute(GET("/blog/create"), req -> ServerResponse.ok().render("blog/create"))
                .andRoute(GET("/blog/{id}"), blogHandler::showBlogPost)
                .andRoute(POST("/blog"), blogHandler::createBlogPost);
    }
}