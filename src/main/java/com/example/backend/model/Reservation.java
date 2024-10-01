package com.example.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

@Entity
public class Reservation {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long reservationId;
private String customerName;
private String customerEmail;

    public Reservation() {
    }
}
