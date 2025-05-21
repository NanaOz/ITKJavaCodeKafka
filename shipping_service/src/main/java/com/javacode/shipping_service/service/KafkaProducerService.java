package com.javacode.shipping_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javacode.shipping_service.dto.OrderDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducerService.class);

    @Value("${spring.kafka.producer.topic}")
    private String topic;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendMessage(OrderDTO orderDTO) {
        try {
            String message = objectMapper.writeValueAsString(orderDTO);
            kafkaTemplate.send(topic, message)
                    .whenComplete((result, ex) -> {
                        if (ex == null) {
                            logger.info("Сообщение отправлено: {}", result);
                        } else {
                            logger.error("Не удалось отправить сообщение", ex);
                        }
                    });
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
