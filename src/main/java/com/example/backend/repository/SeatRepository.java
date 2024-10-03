package com.example.backend.repository;

import com.example.backend.model.Seat;
import com.example.backend.model.Showing;
import com.example.backend.model.Theater; // Importér Theater-klassen
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    List<Seat> findByTheater(Theater theater);
    List<Seat> findByShowing(Showing showing);

}
