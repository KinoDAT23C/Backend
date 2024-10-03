package com.example.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seatId;

    private int rowNumberr;
    private int seatNumber;
    private boolean isReserved;

    @ManyToOne // Mange sæder kan være knyttet til én biografsal (Theater)
    @JoinColumn(name = "theater_id", nullable = false)
    private Theater theater;

    @ManyToOne // Mange sæder kan være knyttet til én specifik forestilling (Showing)
    @JoinColumn(name = "showing_id")
    private Showing showing;


    public Seat() {
    }
}

