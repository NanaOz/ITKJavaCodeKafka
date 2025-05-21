package com.javacode.payment_service.repository;

import com.javacode.payment_service.model.PaymentOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<PaymentOrder, Long> {
}
