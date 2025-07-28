package org.example.work2.Controller;


import org.example.work2.DTO.Order;
import org.example.work2.kafka.OrderProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    @Autowired
    private OrderProducer orderProducer;

    @PostMapping("/orders")
    public String createOrder(@RequestBody Order order) {
        logger.info("收到新订单: {} - 数量: {}, 金额: {}",
                order.getProductName(),
                order.getQuantity(),
                order.getAmount());
        try {
            orderProducer.sendOrder(order);
            logger.info("订单已发送到 Kafka");
            return "Order submitted!";
        } catch (Exception e) {
            logger.error("发送订单到 Kafka 失败", e);
            return "Failed to submit order: " + e.getMessage();
        }
    }
}