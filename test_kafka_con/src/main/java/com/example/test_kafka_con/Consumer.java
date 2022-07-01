package com.example.test_kafka_con;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class Consumer {
    private static final String orderTopic = "${topic.name}";

    private final FoodOrderService foodOrderService;

    @Autowired
    public Consumer( FoodOrderService foodOrderService) {
        this.foodOrderService = foodOrderService;
    }

    @KafkaListener(topics = orderTopic)
    public void consumeMessage(String message) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        log.info("message consumed {}", message);

        FoodOrderDto foodOrderDto = objectMapper.readValue(message, FoodOrderDto.class);
        foodOrderService.persistFoodOrder(foodOrderDto);
    }
}
