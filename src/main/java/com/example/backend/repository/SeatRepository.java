package com.example.backend.repository;

import com.example.backend.model.Seat;
import com.example.backend.model.Showing;
import com.example.backend.model.Theater; // Importér Theater-klassen
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByTheater(Theater theater);
    List<Seat> findByShowing(Showing showing);
    @Query("SELECT s FROM Seat s " +
            "JOIN s.theater t " +
            "LEFT JOIN Reservation r ON s.seatId = r.seat.seatId AND r.showing.showingId = :showingId " +
            "WHERE t.theaterId = :theaterId AND r IS NULL")
    List<Seat> findAvailableSeats(@Param("showingId") Long showingId, @Param("theaterId") Long theaterId);
}


