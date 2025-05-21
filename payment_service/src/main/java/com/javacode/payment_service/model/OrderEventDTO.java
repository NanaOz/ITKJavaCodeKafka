package com.javacode.payment_service.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class OrderEventDTO {
    private Long orderId;
    private BigDecimal amount;
}
