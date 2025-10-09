package com.example.demo.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Map;

@Controller
public class ChatController {

    private final SimpMessagingTemplate messagingTemplate;

    public ChatController(SimpMessagingTemplate messagingTemplate) {
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/chat.message")
    @PreAuthorize("hasRole('USER')")
    public void processMessage(@Payload Map<String, String> payload, Principal principal) {
        String message = payload.get("message");
        String recipient = payload.get("recipient");

        // 发送给特定用户
        messagingTemplate.convertAndSendToUser(
                recipient,
                "/queue/messages",
                Map.of(
                    "sender", principal.getName(),
                    "message", message,
                    "type", "direct"
                )
        );
    }

    @MessageMapping("/chat.broadcast")
    @PreAuthorize("hasRole('USER')")
    public void broadcastMessage(@Payload Map<String, String> payload, Principal principal) {
        String message = payload.get("message");

        // 广播给所有订阅/topic/messages的用户
        messagingTemplate.convertAndSend(
                "/topic/messages",
                Map.of(
                    "sender", principal.getName(),
                    "message", message,
                    "type", "broadcast"
                )
        );
    }

    @MessageMapping("/secured.message")
    @PreAuthorize("hasRole('ADMIN')")
    public void securedMessage(@Payload Map<String, String> payload, Principal principal) {
        String message = "[SECURED] " + payload.get("message");

        // 只有管理员可以发送安全消息到/topic/security
        messagingTemplate.convertAndSend(
                "/topic/security",
                Map.of(
                    "sender", principal.getName(),
                    "message", message,
                    "type", "secured"
                )
        );
    }
}