package com.javacode.shipping_service.controller;

import com.javacode.shipping_service.model.ShippingOrder;
import com.javacode.shipping_service.service.ShippingOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders/shipped")
public class ShippingOrderController {
    private final ShippingOrderService shippingOrderService;

    public ShippingOrderController(ShippingOrderService shippingOrderService) {
        this.shippingOrderService = shippingOrderService;
    }

    @PostMapping
    public ResponseEntity<String> createShipping(@RequestBody Long orderId) {
        shippingOrderService.createShipping(orderId);
        return ResponseEntity.status(HttpStatus.OK).body("Shipping processed successfully for order " + orderId);
    }

    @GetMapping("/{id}")
    public ShippingOrder getOrderById(@PathVariable Long id) {
        return shippingOrderService.getOrderById(id).orElseThrow();
    }
}
