package com.javacode.shipping_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javacode.shipping_service.dto.OrderDTO;
import com.javacode.shipping_service.model.ShippingOrder;
import com.javacode.shipping_service.repository.ShippingOrderRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class KafkaListenerService {
    private final ShippingOrderRepository shippingOrderRepository;

    public KafkaListenerService(ShippingOrderRepository shippingOrderRepository) {
        this.shippingOrderRepository = shippingOrderRepository;
    }

    @KafkaListener(topics = "${spring.kafka.consumer.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void listenOrder(String message, Acknowledgment acknowledgment) throws JsonProcessingException {
        OrderDTO orderDTO = new ObjectMapper().readValue(message, OrderDTO.class);

        if (shippingOrderRepository.existsById(orderDTO.getOrderId())) {
            acknowledgment.acknowledge();
            return;
        }

        ShippingOrder shippingOrder = orderDTO.toOrder();
        shippingOrderRepository.save(shippingOrder);
        acknowledgment.acknowledge();
    }
}
