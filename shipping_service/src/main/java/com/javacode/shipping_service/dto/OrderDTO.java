package com.javacode.shipping_service.dto;

import com.javacode.shipping_service.model.ShippingOrder;
import com.javacode.shipping_service.model.OrderStatus;
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

    public static OrderDTO fromOrder(ShippingOrder shippingOrder) {
        return new OrderDTO(shippingOrder.getId(), shippingOrder.getOrderStatus().toString());
    }

    public ShippingOrder toOrder() {
        try {
            OrderStatus status = OrderStatus.valueOf(orderStatus);

            if (status == OrderStatus.PROCESSING) {
                return new ShippingOrder(orderId, OrderStatus.PACKAGE, LocalDateTime.now());
            } else {
                throw new IllegalArgumentException("Invalid order status: " + orderStatus);
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid order status: " + orderStatus);
        }
    }
}
