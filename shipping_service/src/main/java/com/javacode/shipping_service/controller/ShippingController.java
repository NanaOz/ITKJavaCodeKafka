package com.javacode.shipping_service.controller;

import com.javacode.shipping_service.model.Shipping;
import com.javacode.shipping_service.service.ShippingService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShippingController {
    private final ShippingService shippingService;

    public ShippingController(ShippingService shippingService) {
        this.shippingService = shippingService;
    }

    @PostMapping
    public Shipping createShipping(@RequestBody Long orderId) {
        return shippingService.createShipping(orderId);
    }
}
