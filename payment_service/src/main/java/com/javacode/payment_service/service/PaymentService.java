package com.javacode.payment_service.service;

import com.javacode.payment_service.model.OrderEventDTO;
import com.javacode.payment_service.model.Payment;
import com.javacode.payment_service.model.PaymentStatus;
import com.javacode.payment_service.repository.PaymentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final KafkaTemplate<String, Payment> kafkaTemplate;
    private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);

    public PaymentService(PaymentRepository paymentRepository, KafkaTemplate<String, Payment> kafkaTemplate) {
        this.paymentRepository = paymentRepository;
        this.kafkaTemplate = kafkaTemplate;
    }

    public Payment processPayment(BigDecimal amount, String orderId) {
        Payment payment = new Payment();
        payment.setAmount(amount);
        payment.setOrderId(orderId);
        payment.setStatus(PaymentStatus.PENDING);
        payment.setPaymentDate(LocalDateTime.now());

        payment = paymentRepository.save(payment);
        kafkaTemplate.send("payed_orders", payment);

        return payment;
    }

    @KafkaListener(topics = "new_orders", groupId = "payment_group")
    public void listenNewOrders(OrderEventDTO order) {
        logger.info("Получено новое сообщение о заказе: {}", order);
        try {
            processPayment(order.getAmount(), order.getOrderId().toString());
        } catch (Exception e) {
            logger.error("Ошибка обработки платежа для заказа {}: {}", order.getOrderId(), e.getMessage());
        }
    }
}
