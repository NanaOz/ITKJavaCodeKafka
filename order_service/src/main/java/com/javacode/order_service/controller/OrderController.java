package com.javacode.order_service.controller;

import com.javacode.order_service.model.Order;
import com.javacode.order_service.model.OrderStatus;
import com.javacode.order_service.service.OrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody Order order) {
        return ResponseEntity.status(HttpStatus.CREATED).body(orderService.createOrder(order));
    }

    @PostMapping("/{id}/status")
    public ResponseEntity<Order> updateOrder(@PathVariable Long id, @RequestParam OrderStatus orderStatus) {
        return ResponseEntity.ok(orderService.updateOrder(id, orderStatus));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrder(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.getOrder(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderService.deleteOrder(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
