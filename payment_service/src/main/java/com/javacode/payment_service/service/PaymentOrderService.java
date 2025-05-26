package com.javacode.payment_service.service;

import com.javacode.payment_service.dto.OrderDTO;
import com.javacode.payment_service.model.PaymentOrder;
import com.javacode.payment_service.model.OrderStatus;
import com.javacode.payment_service.repository.PaymentOrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class PaymentOrderService {
    private final PaymentOrderRepository paymentOrderRepository;
    private final KafkaProducerService kafkaProducerService;
    private static final Logger logger = LoggerFactory.getLogger(PaymentOrderService.class);

    public PaymentOrderService(PaymentOrderRepository paymentOrderRepository, KafkaProducerService kafkaProducerService) {
        this.paymentOrderRepository = paymentOrderRepository;
        this.kafkaProducerService = kafkaProducerService;
    }

    @Async
    @Transactional
    public void processPayment(Long id) {
        PaymentOrder paymentOrder = getOrderById(id)
                .orElseThrow(() -> new RuntimeException("Платежное поручение не найдено: " + id));
        paymentOrder.setStatus(OrderStatus.COMPLETED);
//        paymentOrder.setPaymentDate(LocalDateTime.now());
        paymentOrderRepository.save(paymentOrder);
        kafkaProducerService.sendMessage(OrderDTO.fromOrder(paymentOrder));
        logger.info("Получено новое сообщение о заказе: {}", paymentOrder);
    }

    public Optional<PaymentOrder> getOrderById(Long id) {
        return paymentOrderRepository.findById(id);
    }
}
