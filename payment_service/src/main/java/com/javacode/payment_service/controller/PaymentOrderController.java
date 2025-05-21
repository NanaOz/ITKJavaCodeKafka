package com.javacode.payment_service.controller;

import com.javacode.payment_service.model.PaymentOrder;
import com.javacode.payment_service.service.PaymentOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders/payments")
public class PaymentOrderController {
    private final PaymentOrderService paymentOrderService;

    public PaymentOrderController(PaymentOrderService paymentOrderService) {
        this.paymentOrderService = paymentOrderService;
    }

    @PostMapping
    public ResponseEntity<String> processPayment(@RequestBody Long id) {
        paymentOrderService.processPayment(id);
        return ResponseEntity.status(HttpStatus.OK).body("Order processed successfully" + id);
    }

    @GetMapping("/{id}")
    public PaymentOrder getOrderById(@PathVariable Long id) {
        return paymentOrderService.getOrderById(id).orElseThrow();
    }
}
