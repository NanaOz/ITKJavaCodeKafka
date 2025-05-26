package com.javacode.payment_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javacode.payment_service.dto.OrderDTO;
import com.javacode.payment_service.model.OrderStatus;
import com.javacode.payment_service.model.PaymentOrder;
import com.javacode.payment_service.repository.PaymentOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class KafkaListenerService {
    private final PaymentOrderRepository paymentOrderRepository;
    private static final Logger logger = LoggerFactory.getLogger(KafkaListenerService.class);
    private final ObjectMapper objectMapper; // Для парсинга JSON

    public KafkaListenerService(PaymentOrderRepository paymentOrderRepository, ObjectMapper objectMapper) {
        this.paymentOrderRepository = paymentOrderRepository;
        this.objectMapper = objectMapper;
    }

    @KafkaListener(topics = "${spring.kafka.consumer.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void listenOrder(String message, Acknowledgment ack) throws JsonProcessingException {
        try {
            OrderDTO orderDTO = new ObjectMapper().readValue(message, OrderDTO.class);
            logger.info("Получено сообщение о заказе: {}", orderDTO.getOrderId());

            if (!paymentOrderRepository.existsById(orderDTO.getOrderId())) {
                PaymentOrder paymentOrder = orderDTO.toOrder();
                paymentOrderRepository.save(paymentOrder);
                logger.info("Сохраненно: {}", paymentOrder);
            }

            ack.acknowledge();
        } catch (Exception e) {
            logger.error("Сообщение об ошибке при обработке сообщения Kafka: {}", message, e);
        }
    }

}
