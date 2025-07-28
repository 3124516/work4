package org.example.work2.kafka;

import org.example.work2.DTO.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class OrderConsumer {

    // 使用自动注入 (确保 WebSocketConfig 配置了)
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @KafkaListener(topics = "orders", groupId = "order-group")
    public void consume(Order order) {
        // 添加时间戳
        order.setTimestamp(System.currentTimeMillis());

        // 推送给订阅 "/topic/orders" 的前端客户端
        messagingTemplate.convertAndSend("/topic/orders", order);
    }
}