package com.javacode.notifications_service.dto;

import com.javacode.notifications_service.model.NotificationOrder;
import com.javacode.notifications_service.model.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long orderId;
    private String orderStatus;

    public static OrderDTO fromOrder(NotificationOrder notificationOrder) {
        return new OrderDTO(notificationOrder.getId(), notificationOrder.getOrderStatus().toString());
    }

    public NotificationOrder toOrder() {
        OrderStatus status = OrderStatus.valueOf(orderStatus);
        return new NotificationOrder(orderId, status, LocalDateTime.now());
    }
}
