package com.javacode.order_service.dto;

import com.javacode.order_service.model.Order;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class OrderDTO {
    private Long orderId;
    private String orderStatus;

    public static OrderDTO fromOrder(Order order) {
        return new OrderDTO(order.getId(), order.getStatus().name());
    }
}
