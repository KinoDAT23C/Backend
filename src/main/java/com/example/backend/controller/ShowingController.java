package com.example.backend.controller;

import com.example.backend.model.Movie;
import com.example.backend.model.Showing;
import com.example.backend.repository.MovieRepository;
import com.example.backend.repository.ShowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/showings")
public class ShowingController {

    @Autowired
    private ShowingRepository showingRepository;

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping("/{movieId}")
    public ResponseEntity<List<Showing>> getShowingsForMovie(@PathVariable Long movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found with id " + movieId));
        List<Showing> showings = showingRepository.findByMovie(movie);
        return ResponseEntity.ok(showings);
    }

}
