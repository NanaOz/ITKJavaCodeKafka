package com.javacode.order_service.service;

import com.javacode.order_service.model.Order;
import com.javacode.order_service.model.OrderStatus;
import com.javacode.order_service.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderRepository repository;
    private final KafkaTemplate<String, Order> kafkaTemplate;
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    public OrderService(OrderRepository repository, KafkaTemplate<String, Order> kafkaTemplate) {
        this.repository = repository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Order createOrder(Order order) {
        order.setStatus(OrderStatus.CREATED);
        Order savedOrder = repository.save(order);
        kafkaTemplate.send("new_orders", savedOrder);
        logger.info("Информация о заказе отправлеа {}", savedOrder);
        return savedOrder;
    }

    public Order updateOrder(Long id, OrderStatus status) {
        Order order = repository.findById(id).orElseThrow();
        order.setStatus(status);
        return repository.save(order);
    }

    public Order getOrder(Long id) {
        return repository.findById(id).orElseThrow();
    }
}
