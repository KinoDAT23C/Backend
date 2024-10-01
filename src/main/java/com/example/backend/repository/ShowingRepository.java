package com.example.backend.repository;

import com.example.backend.model.Movie;
import com.example.backend.model.Showing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowingRepository extends JpaRepository<Showing, Long> {
    List<Showing> findByMovie(Movie movie);
}
