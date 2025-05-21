package com.javacode.notifications_service.service;

import com.javacode.notifications_service.model.Notification;
import com.javacode.notifications_service.model.ShippingEventDTO;
import com.javacode.notifications_service.repository.NotificationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public Notification createNotification(String message, String recipient) {
        Notification notification = new Notification();
        notification.setMessage(message);
        notification.setRecipient(recipient);

        return notificationRepository.save(notification);
    }

    @KafkaListener(topics = "sent_orders", groupId = "notification_group")
    public void listenSentOrders(ShippingEventDTO shippingEventDTO) {
        logger.info("Получено сообщение об отгрузке: {}", shippingEventDTO);
        try {
            String message = String.format("Ваш заказ %s был успешно доставлен. Трек-номер: %s",
                    shippingEventDTO.getOrderId(),
                    shippingEventDTO.getTrackingNumber());

            createNotification(message, "");
        } catch (Exception e) {
            logger.error("Ошибка при отправке уведомления для заказа {}: {}", shippingEventDTO.getOrderId(), e.getMessage());
        }
    }
}
