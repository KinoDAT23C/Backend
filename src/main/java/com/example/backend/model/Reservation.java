package com.example.backend.model;

import jakarta.persistence.*;
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

@ManyToOne
@JoinColumn(name = "showing_id", nullable = false)
private Showing showing;

@ManyToOne
@JoinColumn(name = "seat_id", nullable = false)
private Seat seat;

    public Reservation() {
    }
}
