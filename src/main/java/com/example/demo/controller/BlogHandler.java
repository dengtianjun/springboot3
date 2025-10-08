package com.example.demo.controller;

import com.example.demo.model.BlogPost;
import com.example.demo.service.BlogService;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class BlogHandler {
    
    private final BlogService blogService;
    
    public BlogHandler(BlogService blogService) {
        this.blogService = blogService;
    }
    
    public Mono<ServerResponse> listBlogPosts(ServerRequest request) {
        return ok().render("blog/list", java.util.Map.of("blogPosts", blogService.getAllBlogPosts()));
    }
    
    public Mono<ServerResponse> showBlogPost(ServerRequest request) {
        String idStr = request.pathVariable("id");
        try {
            Long id = Long.parseLong(idStr);
            return blogService.getBlogPostById(id)
                    .flatMap(post -> ok().render("blog/detail", java.util.Map.of("blogPost", post)))
                    .switchIfEmpty(ServerResponse.notFound().build());
        } catch (NumberFormatException e) {
            return ServerResponse.badRequest().build();
        }
    }
    
    public Mono<ServerResponse> createBlogPost(ServerRequest request) {
        return request.formData()
                .flatMap(formData -> {
                    BlogPost blogPost = new BlogPost();
                    blogPost.setTitle(formData.getFirst("title"));
                    blogPost.setContent(formData.getFirst("content"));
                    blogPost.setAuthor(formData.getFirst("author"));
                    return blogService.createBlogPost(blogPost);
                })
                .then(ok().render("blog/success"));
    }
}