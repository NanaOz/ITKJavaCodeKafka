package com.javacode.order_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "customerId", nullable = false)
    private Long customerId;

    @Column(name = "productName")
    private String productName;

    @Column(name = "productCount")
    private int productCount;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name = "totalPrice", nullable = false)
    private BigDecimal totalPrice;
}