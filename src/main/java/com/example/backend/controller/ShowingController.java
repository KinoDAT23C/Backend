package com.example.backend.controller;

import com.example.backend.model.Movie;
import com.example.backend.model.Showing;
import com.example.backend.repository.MovieRepository;
import com.example.backend.repository.ShowingRepository;
import com.example.backend.service.ShowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

    @Autowired
    private ShowingService showingService;

    @GetMapping("/{movieId}")
    public ResponseEntity<List<Showing>> getShowingsForMovie(@PathVariable Long movieId) {
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found with id " + movieId));
        List<Showing> showings = showingRepository.findByMovie(movie);
        return ResponseEntity.ok(showings);
    }

    // Create a new showing
    /*@PostMapping
    public ResponseEntity<Showing> createShowing(@RequestBody Showing showing) {
        Showing createdShowing = showingService.createShowing(showing);
        return new ResponseEntity<>(createdShowing, HttpStatus.CREATED);
    }*/
    @PostMapping
    public ResponseEntity<Showing> createShowing(@RequestBody Showing showing) {
        Showing savedShowing = showingRepository.save(showing);
        return ResponseEntity.ok(savedShowing);
    }

    // Get all showings
    @GetMapping
    public ResponseEntity<List<Showing>> getAllShowings() {
        List<Showing> showings = showingService.getAllShowings();
        return new ResponseEntity<>(showings, HttpStatus.OK);
    }

    // Get showings for the next 3 months
    @GetMapping("/upcoming")
    public ResponseEntity<List<Showing>> getShowingsForNextThreeMonths() {
        List<Showing> showings = showingService.getShowingsForNextThreeMonths();
        return new ResponseEntity<>(showings, HttpStatus.OK);
    }

    // Get showings by theater
    @GetMapping("/theater/{theaterId}")
    public ResponseEntity<List<Showing>> getShowingsByTheater(@PathVariable Long theaterId) {
        List<Showing> showings = showingService.getShowingsByTheater(theaterId);
        return new ResponseEntity<>(showings, HttpStatus.OK);
    }

    // Update an existing showing
    /*@PutMapping("/{id}")
    public ResponseEntity<Showing> updateShowing(@PathVariable Long id, @RequestBody Showing updatedShowing) {
        Showing showing = showingService.updateShowing(id, updatedShowing);
        return new ResponseEntity<>(showing, HttpStatus.OK);
    }*/

    // Delete a showing
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteShowing(@PathVariable Long id) {
        showingService.deleteShowing(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
