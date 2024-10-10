package com.example.backend.controller;

import com.example.backend.model.Movie;
import com.example.backend.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @PostMapping("/createWithImage") // Gør de to overstående endpoints overflødige
    public ResponseEntity<Movie> createMovieWithImage(
            @RequestBody Movie movie,
            @RequestParam String imageUrl) {

        // Sæt imageUrl på det nye Movie-objekt
        movie.setImageUrl(imageUrl);

        // Gem filmen med billedet
        Movie createdMovie = movieRepository.save(movie);

        return ResponseEntity.ok(createdMovie);
    }

    @DeleteMapping("/{movieId}")
    public ResponseEntity<String> deleteMovie(@PathVariable Long movieId) {
        // Tjek om filmen findes
        boolean exists = movieRepository.existsById(movieId);
        if (!exists) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Film ikke fundet med id " + movieId);
        }

        // Slet filmen
        movieRepository.deleteById(movieId);

        return ResponseEntity.ok("Film med id " + movieId + " er blevet slettet.");
    }

}