package com.javacode.payment_service.model;

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
@Table(name = "payment")
public class PaymentOrder {
    @Id
    private Long id;
    private OrderStatus status;
    private LocalDateTime paymentDate;
}