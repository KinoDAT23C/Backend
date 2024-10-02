package com.example.backend.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalTime;
@Getter
@Setter

@Entity
public class Showing {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
private Long showingId;

private LocalDate date;
private LocalTime startTime;
private LocalTime endTime;
private String movieTitle;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    public Showing() {
    }
}
