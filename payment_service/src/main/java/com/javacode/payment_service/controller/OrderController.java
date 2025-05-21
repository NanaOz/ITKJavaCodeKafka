package com.javacode.payment_service.controller;

import com.javacode.payment_service.model.PaymentOrder;
import com.javacode.payment_service.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders/payments")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<String> processPayment(@RequestBody Long id) {
        orderService.processPayment(id);
        return ResponseEntity.status(HttpStatus.OK).body("Order processed successfully" + id);
    }

    @GetMapping("/{id}")
    public PaymentOrder getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id).orElseThrow();
    }
}
