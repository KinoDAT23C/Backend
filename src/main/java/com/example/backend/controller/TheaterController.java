package com.example.backend.controller;

import com.example.backend.model.Seat;
import com.example.backend.model.Theater;
import com.example.backend.repository.SeatRepository;
import com.example.backend.repository.TheaterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/theaters")
public class TheaterController {

    @Autowired
    private TheaterRepository theaterRepository;

    @Autowired
    private SeatRepository seatRepository;

    @PostMapping // skaber den lille og store biograf sal
    public ResponseEntity<Theater> createTheater(@RequestBody Theater theater) {
        Theater createdTheater = theaterRepository.save(theater);
        return ResponseEntity.ok(createdTheater);
    }

    @PostMapping("/generateSeats/{theaterId}") // Dette endpoint indsætter alle sæder så vi ikke skal gøre det manuelt
    public ResponseEntity<String> generateSeats(@PathVariable Long theaterId) {
        Theater theater = theaterRepository.findById(theaterId)
                .orElseThrow(() -> new RuntimeException("Theater not found with id " + theaterId));

        int totalRows = theater.getTotalRows();
        int totalSeatsPerRow = theater.getTotalSeatsPerRow();

        for (int row = 1; row <= totalRows; row++) {
            for (int seatNumber = 1; seatNumber <= totalSeatsPerRow; seatNumber++) {
                Seat seat = new Seat();
                seat.setRowNumberr(row);
                seat.setSeatNumber(seatNumber);
                seat.setReserved(false);
                seat.setTheater(theater);
                seatRepository.save(seat);
            }
        }

        return ResponseEntity.ok("Seats generated successfully for Theater ID " + theaterId);
    }

}
