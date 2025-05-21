package com.javacode.shipping_service.service;

import com.javacode.shipping_service.model.PaymentEventDTO;
import com.javacode.shipping_service.model.Shipping;
import com.javacode.shipping_service.repository.ShippingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ShippingService {
    private final ShippingRepository shippingRepository;
    private final KafkaTemplate<String, Shipping> kafkaTemplate;
    private static final Logger logger = LoggerFactory.getLogger(ShippingService.class);

    public ShippingService(ShippingRepository shippingRepository, KafkaTemplate<String, Shipping> kafkaTemplate) {
        this.shippingRepository = shippingRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Shipping createShipping(Long orderId) {
        Shipping shipping = new Shipping();
        shipping.setOrderId(orderId);
        shipping.setShippingDate(LocalDateTime.now());
        shipping.setDelivered(false);
        shipping.setTrackingNumber(UUID.randomUUID().toString());

        shipping = shippingRepository.save(shipping);
        kafkaTemplate.send("sent_orders", shipping);
        logger.info("Отгрузка создана и сообщение отправлено в sent_orders: {}", shipping);
        return shipping;
    }

    @KafkaListener(topics = "payed_orders", groupId = "shipping_group")
    public void listenPayedOrders(PaymentEventDTO paymentEventDTO) {
        logger.info("Получено сообщение об оплате: {}", paymentEventDTO);
        try {
            createShipping(paymentEventDTO.getOrderId());
        } catch (Exception e) {
            logger.error("Ошибка обработки отгрузки для заказа {}: {}", paymentEventDTO.getOrderId(), e.getMessage());
        }
    }
}
