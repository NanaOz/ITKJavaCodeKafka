package com.javacode.notifications_service.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ShippingEventDTO {
    Long shippingId;
    String orderId;
    String trackingNumber;
    boolean isDelivered;
}
