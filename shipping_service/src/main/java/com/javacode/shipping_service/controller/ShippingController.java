package com.javacode.shipping_service.controller;

import com.javacode.shipping_service.model.ShippingOrder;
import com.javacode.shipping_service.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders/shipped")
public class ShippingController {
    private final OrderService orderService;

    public ShippingController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<String> createShipping(@RequestBody Long orderId) {
        orderService.createShipping(orderId);
        return ResponseEntity.status(HttpStatus.OK).body("Shipping processed successfully for order " + orderId);
    }

    @GetMapping("/{id}")
    public ShippingOrder getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id).orElseThrow();
    }
}
