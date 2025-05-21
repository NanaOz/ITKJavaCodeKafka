package com.javacode.notifications_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notification")
public class NotificationOrder {
    @Id
    private Long id;
    private OrderStatus orderStatus;
    private LocalDateTime orderDate;
}
