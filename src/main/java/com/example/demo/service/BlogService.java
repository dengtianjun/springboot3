package com.example.demo.service;

import com.example.demo.model.BlogPost;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BlogService {
    
    private final ConcurrentHashMap<Long, BlogPost> blogPosts = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);
    
    public BlogService() {
        // 添加一些示例数据
        BlogPost post1 = new BlogPost(
                idGenerator.getAndIncrement(),
                "欢迎来到我的博客",
                "这是我的第一篇博客文章。WebFlux是一个非常强大的响应式编程框架。",
                "作者A",
                LocalDateTime.now().minusDays(2)
        );
        
        BlogPost post2 = new BlogPost(
                idGenerator.getAndIncrement(),
                "WebFlux入门指南",
                "在这篇文章中，我们将学习如何使用Spring WebFlux构建响应式Web应用程序。",
                "作者B",
                LocalDateTime.now().minusDays(1)
        );
        
        blogPosts.put(post1.getId(), post1);
        blogPosts.put(post2.getId(), post2);
    }
    
    public Flux<BlogPost> getAllBlogPosts() {
        return Flux.fromIterable(blogPosts.values())
                .sort((p1, p2) -> p2.getPublishDate().compareTo(p1.getPublishDate())).delayElements(Duration.ofMillis(500));
    }
    
    public Mono<BlogPost> getBlogPostById(Long id) {
        BlogPost post = blogPosts.get(id);
        return post != null ? Mono.just(post) : Mono.empty();
    }
    
    public Mono<BlogPost> createBlogPost(BlogPost blogPost) {
        blogPost.setId(idGenerator.getAndIncrement());
        blogPost.setPublishDate(LocalDateTime.now());
        blogPosts.put(blogPost.getId(), blogPost);
        return Mono.just(blogPost);
    }
}