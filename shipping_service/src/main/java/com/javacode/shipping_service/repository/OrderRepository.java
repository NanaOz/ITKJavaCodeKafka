package com.javacode.shipping_service.repository;

import com.javacode.shipping_service.model.ShippingOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<ShippingOrder, Long> {
}
