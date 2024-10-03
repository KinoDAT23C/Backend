package com.example.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter

@Entity
public class Orderr {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long orderId;

private int quantity;
private BigDecimal totalPrice;

@ManyToOne
@JoinColumn(name = "reservation_id", nullable = false)
private Reservation reservation;



    public Orderr() {
    }
}
