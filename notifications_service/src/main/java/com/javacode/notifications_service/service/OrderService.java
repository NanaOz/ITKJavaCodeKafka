package com.javacode.notifications_service.service;

import com.javacode.notifications_service.model.NotificationOrder;
import com.javacode.notifications_service.model.OrderStatus;
import com.javacode.notifications_service.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void createNotification(Long orderId) {
        NotificationOrder notificationOrder = getOrderById(orderId).orElseThrow();
        notificationOrder.setOrderStatus(OrderStatus.COMPLETED);
        notificationOrder.setOrderDate(LocalDateTime.now());

        orderRepository.save(notificationOrder);
    }

    public Optional<NotificationOrder> getOrderById(Long id) {
        return orderRepository.findById(id);
    }
}
