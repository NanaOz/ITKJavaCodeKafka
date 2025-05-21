package com.javacode.shipping_service.service;

import com.javacode.shipping_service.dto.OrderDTO;
import com.javacode.shipping_service.model.ShippingOrder;
import com.javacode.shipping_service.model.OrderStatus;
import com.javacode.shipping_service.repository.ShippingOrderRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ShippingOrderService {
    private final ShippingOrderRepository shippingOrderRepository;
    private final KafkaProducerService kafkaProducerService;
    private static final Logger logger = LoggerFactory.getLogger(ShippingOrderService.class);

    public ShippingOrderService(ShippingOrderRepository shippingOrderRepository, KafkaProducerService kafkaProducerService) {
        this.shippingOrderRepository = shippingOrderRepository;
        this.kafkaProducerService = kafkaProducerService;
    }

    @Transactional
    public void createShipping(Long orderId) {
        ShippingOrder shippingOrder = getOrderById(orderId).orElseThrow();
        shippingOrder.setOrderStatus(OrderStatus.SHIPPED);
        kafkaProducerService.sendMessage(OrderDTO.fromOrder(shippingOrder));
        logger.info("Отгрузка создана и сообщение отправлено в sent_orders: {}", shippingOrder);
    }

    public Optional<ShippingOrder> getOrderById(Long id) {
        return shippingOrderRepository.findById(id);
    }
}
