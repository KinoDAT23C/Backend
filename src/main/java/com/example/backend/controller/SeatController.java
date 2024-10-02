package com.example.backend.controller;

import com.example.backend.model.Seat;
import com.example.backend.model.Showing;
import com.example.backend.model.Theater;
import com.example.backend.repository.SeatRepository;
import com.example.backend.repository.ShowingRepository;
import com.example.backend.repository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
public class SeatController {

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private TheaterRepository theaterRepository;

    @Autowired
    private ShowingRepository showingRepository;


    @GetMapping("/theater/{theaterId}") // viser os alle sæderne som er forbundet til en sal. Dog er "showing: null" på alle sæder da det ikke er integreret endnu
    public ResponseEntity<List<Seat>> getSeatsByTheater(@PathVariable Long theaterId) {
        Theater theater = theaterRepository.findById(theaterId)
                .orElseThrow(() -> new RuntimeException("Theater not found with id " + theaterId));
        List<Seat> seats = seatRepository.findByTheater(theater);
        return ResponseEntity.ok(seats);
    }
    @GetMapping("/showing/{showingId}") // virker ikke endnu. Der mangler et PostMapping endpoint i showings så de kan blive oprettet.
    public ResponseEntity<List<Seat>> getSeatsByShowing(@PathVariable Long showingId) {
        Showing showing = showingRepository.findById(showingId)
                .orElseThrow(() -> new RuntimeException("Showing not found with id " + showingId));

        // Hent alle sæder for en specifik showing
        List<Seat> seats = seatRepository.findByShowing(showing);
        return ResponseEntity.ok(seats);
    }


    @PostMapping // kan evt slettes - da der er et endpoint i theaterController klassen som genererer dem automatisk.
    public ResponseEntity<Seat> createSeat(@RequestBody Seat seat) {
        Theater theater = theaterRepository.findById(seat.getTheater().getTheaterId())
                .orElseThrow(() -> new RuntimeException("Theater not found with id " + seat.getTheater().getTheaterId()));

        seat.setTheater(theater); // Sæt theater i seat objektet
        Seat createdSeat = seatRepository.save(seat);
        return ResponseEntity.ok(createdSeat);
    }


    @PutMapping("/{seatId}/reserve") // Så vi kan ændre et sæde til at være reserveret
    public ResponseEntity<Seat> reserveSeat(@PathVariable Long seatId) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Seat not found with id " + seatId));

        if (seat.isReserved()) {
            return ResponseEntity.status(409).body(null); // Konflikt hvis sædet allerede er reserveret
        }

        seat.setReserved(true);
        Seat updatedSeat = seatRepository.save(seat);
        return ResponseEntity.ok(updatedSeat);
    }

    // Update: Cancel reservation for a seat
    @PutMapping("/{seatId}/cancel") // Så vi kan ændre et reserveret sæde tilbage til ledigt
    public ResponseEntity<Seat> cancelSeatReservation(@PathVariable Long seatId) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new RuntimeException("Seat not found with id " + seatId));

        if (!seat.isReserved()) {
            return ResponseEntity.status(400).body(null); // Bad request hvis sædet ikke er reserveret
        }

        seat.setReserved(false);
        Seat updatedSeat = seatRepository.save(seat);
        return ResponseEntity.ok(updatedSeat);
    }
}
