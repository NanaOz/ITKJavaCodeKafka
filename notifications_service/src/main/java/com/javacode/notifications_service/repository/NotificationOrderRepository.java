package com.javacode.notifications_service.repository;

import com.javacode.notifications_service.model.NotificationOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationOrderRepository extends JpaRepository<NotificationOrder, Long> {
}
