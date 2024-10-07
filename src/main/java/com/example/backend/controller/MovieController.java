package com.example.backend.controller;

import com.example.backend.model.Movie;
import com.example.backend.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;


    @GetMapping
    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    @PostMapping
    public Movie createMovie(@RequestBody Movie movie) {
        return movieRepository.save(movie);
    }

    @GetMapping("/available")
    public List<Movie> getAvailableMovies() {
        List<Movie> movies = movieRepository.findAll();
        return movies;
    }

    // Endpoint for at opdatere imageUrl for en bestemt film
    @PatchMapping("/{movieId}/updateImage")
    public ResponseEntity<Movie> updateMovieImageUrl(
            @PathVariable Long movieId,
            @RequestParam String imageUrl) {

        // Find filmen baseret på movieId
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Film ikke fundet med id " + movieId));

        // Opdater imageUrl for den fundne film
        movie.setImageUrl(imageUrl);

        // Gem den opdaterede film
        Movie updatedMovie = movieRepository.save(movie);
        return ResponseEntity.ok(updatedMovie);
    }
}
