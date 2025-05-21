package com.javacode.order_service.service;

import com.javacode.order_service.dto.OrderDTO;
import com.javacode.order_service.model.Order;
import com.javacode.order_service.model.OrderStatus;
import com.javacode.order_service.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class OrderService {
    private final OrderRepository repository;
    private final KafkaProducerService kafkaProducerService;

    public OrderService(OrderRepository repository, KafkaProducerService kafkaProducerService) {
        this.repository = repository;
        this.kafkaProducerService = kafkaProducerService;
    }

    @Transactional
    public Order createOrder(Order order) {
        Order savedOrder = repository.save(order);
        kafkaProducerService.sendMessage(OrderDTO.fromOrder(savedOrder));
        return savedOrder;
    }

    @Transactional
    public Order updateOrder(Long id, OrderStatus status) {
        Order order = repository.findById(id).orElseThrow();
        order.setStatus(status);
        return repository.save(order);
    }

    public Order getOrder(Long id) {
        return repository.findById(id).orElseThrow();
    }

    @Transactional
    public void deleteOrder(Long id) {
        repository.deleteById(id);
    }
}
