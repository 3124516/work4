package org.example.work2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 客户端将使用此端点连接 WebSocket
        registry.addEndpoint("/ws")
                .setAllowedOriginPatterns("*") // 允许所有来源
                .withSockJS(); // 支持 SockJS 回退
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 设置消息代理前缀（前端订阅路径）
        registry.enableSimpleBroker("/topic");

        // 设置应用程序目的地前缀（服务端处理路径）
        registry.setApplicationDestinationPrefixes("/app");
    }
}