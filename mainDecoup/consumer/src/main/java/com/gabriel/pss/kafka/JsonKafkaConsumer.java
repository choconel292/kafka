package com.gabriel.pss.kafka;

import com.gabriel.pss.payload.Order;
import com.gabriel.pss.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class JsonKafkaConsumer {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonKafkaConsumer.class);

    private final OrderRepository orderRepository;

    private KafkaTemplate<String, Order> kafkaTemplate;

    public JsonKafkaConsumer(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @KafkaListener(topics = "pss_order", groupId = "myGroup")
    public void consume(Order order)
    {
        LOGGER.info("Receiving and saving order...");
        orderRepository.save(order);
    }
}
