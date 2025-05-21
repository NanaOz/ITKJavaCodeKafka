package com.javacode.order_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.javacode.order_service.dto.OrderDTO;
import org.springframework.kafka.support.SendResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${spring.kafka.producer.topic}")
    private String topic;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendMessage(OrderDTO orderDTO) {
        try {
            String message = objectMapper.writeValueAsString(orderDTO);
            CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(topic, message);
            future.join();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}