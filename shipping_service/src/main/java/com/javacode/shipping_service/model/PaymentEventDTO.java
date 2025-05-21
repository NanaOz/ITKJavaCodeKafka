package com.javacode.shipping_service.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentEventDTO {
    private Long orderId;
    private String trackingNumber;
    private String customerEmail;
}
