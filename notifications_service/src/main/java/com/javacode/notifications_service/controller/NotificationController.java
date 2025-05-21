package com.javacode.notifications_service.controller;

import com.javacode.notifications_service.model.Notification;
import com.javacode.notifications_service.service.NotificationService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notification")
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    public Notification createNotification(@RequestParam String message, @RequestParam String recipient) {
        return notificationService.createNotification(message, recipient);
    }
}
