package com.javacode.notifications_service.controller;

import com.javacode.notifications_service.service.NotificationOrderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notification")
public class NotificationOrderController {
    private final NotificationOrderService notificationOrderService;

    public NotificationOrderController(NotificationOrderService notificationOrderService) {
        this.notificationOrderService = notificationOrderService;
    }

    @PostMapping
    public ResponseEntity<String> createNotification(@RequestBody Long orderId) {
        notificationOrderService.createNotification(orderId);
        return ResponseEntity.status(HttpStatus.OK).body("Shipping processed successfully for order " + orderId);
    }
}
