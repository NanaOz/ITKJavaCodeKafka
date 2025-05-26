package com.javacode.payment_service.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.javacode.payment_service.model.PaymentOrder;
import com.javacode.payment_service.model.OrderStatus;
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

    public static OrderDTO fromOrder(PaymentOrder paymentOrder) {
        return new OrderDTO(paymentOrder.getId(), paymentOrder.getStatus().toString());
    }

    public PaymentOrder toOrder() {
        try {
            OrderStatus status = OrderStatus.valueOf(orderStatus);

            if (status == OrderStatus.CREATED) {
                return new PaymentOrder(orderId, OrderStatus.PROCESSING, LocalDateTime.now());
            } else {
                throw new IllegalArgumentException("Invalid order status: " + orderStatus);
            }
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid order status: " + orderStatus);
        }
    }

    public static OrderDTO fromJson(String json) throws JsonProcessingException {
        return new ObjectMapper().readValue(json, OrderDTO.class);
    }
}
