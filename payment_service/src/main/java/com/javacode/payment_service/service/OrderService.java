package com.javacode.payment_service.service;

import com.javacode.payment_service.dto.OrderDTO;
import com.javacode.payment_service.model.PaymentOrder;
import com.javacode.payment_service.model.OrderStatus;
import com.javacode.payment_service.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final KafkaProducerService kafkaProducerService;
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    public OrderService(OrderRepository orderRepository, KafkaProducerService kafkaProducerService) {
        this.orderRepository = orderRepository;
        this.kafkaProducerService = kafkaProducerService;
    }

    public void processPayment(Long id) {
        PaymentOrder paymentOrder = getOrderById(id).orElseThrow();
        paymentOrder.setStatus(OrderStatus.COMPLETED);
        paymentOrder.setPaymentDate(LocalDateTime.now());
        orderRepository.save(paymentOrder);
        kafkaProducerService.sendMessage(OrderDTO.fromOrder(paymentOrder));
        logger.info("Получено новое сообщение о заказе: {}", paymentOrder);
    }

    public Optional<PaymentOrder> getOrderById(Long id) {
        return orderRepository.findById(id);
    }
}
