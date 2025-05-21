package com.javacode.payment_service.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javacode.payment_service.dto.OrderDTO;
import com.javacode.payment_service.model.PaymentOrder;
import com.javacode.payment_service.repository.PaymentOrderRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;

@Service
public class KafkaListenerService {
    private final PaymentOrderRepository paymentOrderRepository;

    public KafkaListenerService(PaymentOrderRepository paymentOrderRepository) {
        this.paymentOrderRepository = paymentOrderRepository;
    }

    @KafkaListener(topics = "${spring.kafka.consumer.topic}", groupId = "${spring.kafka.consumer.group-id}")
    public void listenOrder(String message, Acknowledgment ack) throws JsonProcessingException {
        OrderDTO orderDTO = new ObjectMapper().readValue(message, OrderDTO.class);

        if (paymentOrderRepository.existsById(orderDTO.getOrderId())) {
            ack.acknowledge();
            return;
        }

        PaymentOrder paymentOrder = orderDTO.toOrder();
        paymentOrderRepository.save(paymentOrder);

        ack.acknowledge();
    }
}
