package com.javacode.notifications_service.service;

import com.javacode.notifications_service.model.NotificationOrder;
import com.javacode.notifications_service.model.OrderStatus;
import com.javacode.notifications_service.repository.NotificationOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class NotificationOrderService {
    private final NotificationOrderRepository notificationOrderRepository;
    private static final Logger logger = LoggerFactory.getLogger(NotificationOrderService.class);

    public NotificationOrderService(NotificationOrderRepository notificationOrderRepository) {
        this.notificationOrderRepository = notificationOrderRepository;
    }

    public void createNotification(Long orderId) {
        NotificationOrder notificationOrder = getOrderById(orderId).orElseThrow();
        notificationOrder.setOrderStatus(OrderStatus.COMPLETED);
        notificationOrder.setOrderDate(LocalDateTime.now());

        notificationOrderRepository.save(notificationOrder);
    }

    public Optional<NotificationOrder> getOrderById(Long id) {
        return notificationOrderRepository.findById(id);
    }
}
