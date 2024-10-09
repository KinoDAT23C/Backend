package com.example.backend.controller;

import com.example.backend.model.Reservation;
import com.example.backend.model.Seat;
import com.example.backend.model.Showing;
import com.example.backend.repository.ReservationRepository;
import com.example.backend.repository.SeatRepository;
import com.example.backend.repository.ShowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ShowingRepository showingRepository;


    @PostMapping
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {

        Showing showing = showingRepository.findById(reservation.getShowing().getShowingId()).orElse(null);
        Seat seat = seatRepository.findById(reservation.getSeat().getSeatId()).orElse(null);

        if (showing == null || seat == null) {
            return ResponseEntity.badRequest().body(null);
        }

        reservation.setShowing(showing);
        reservation.setSeat(seat);

        Reservation savedReservation = reservationRepository.save(reservation);
        System.out.println("Received Reservation: " + reservation);
        return ResponseEntity.ok(savedReservation);

    }
}
