package com.javacode.shipping_service.model;

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
@Table(name = "shipping")
public class ShippingOrder {
    @Id
    private Long id;
    private OrderStatus orderStatus;
    private LocalDateTime shippingDate;
}
