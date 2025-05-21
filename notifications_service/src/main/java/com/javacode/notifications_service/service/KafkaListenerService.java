package com.javacode.notifications_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javacode.notifications_service.dto.OrderDTO;
import com.javacode.notifications_service.model.NotificationOrder;
import com.javacode.notifications_service.repository.NotificationOrderRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class KafkaListenerService {
    private final NotificationOrderRepository notificationOrderRepository;

    public KafkaListenerService(NotificationOrderRepository notificationOrderRepository) {
        this.notificationOrderRepository = notificationOrderRepository;
    }

    @KafkaListener(topics = "${spring.kafka.consumer.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void listenOrder(String message, Acknowledgment ack) throws JsonProcessingException {
        OrderDTO orderDTO = new ObjectMapper().readValue(message, OrderDTO.class);

        if (notificationOrderRepository.existsById(orderDTO.getOrderId())) {
            ack.acknowledge();
            return;
        }

        NotificationOrder notificationOrder = orderDTO.toOrder();
        notificationOrderRepository.save(notificationOrder);

        ack.acknowledge();
    }
}
