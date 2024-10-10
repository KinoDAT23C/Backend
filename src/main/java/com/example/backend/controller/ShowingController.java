package com.example.backend.controller;

import com.example.backend.model.Movie;
import com.example.backend.model.Showing;
import com.example.backend.model.Theater;
import com.example.backend.repository.MovieRepository;
import com.example.backend.repository.ShowingRepository;
import com.example.backend.repository.TheaterRepository;
import com.example.backend.service.ShowingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
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

    @Autowired
    private TheaterRepository theaterRepository;

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

    @PostMapping("/generate") // NY
    public ResponseEntity<String> generateShowings(
            @RequestParam Long movieId,
            @RequestParam String startDate,
            @RequestParam int monthsAhead,
            @RequestParam(required = false) String largeTheaterStartTime,  // Valgfri parameter for store sal
            @RequestParam(required = false) String smallTheaterStartTime)  // Valgfri parameter for lille sal
    {
        // Find filmen ved det angivne id
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Movie not found with id " + movieId));

        // Find biografer (theaters)
        List<Theater> theaters = theaterRepository.findAll();
        if (theaters.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No theaters found to create showings");
        }

        // Startdato konverteres fra string til LocalDate
        LocalDate start = LocalDate.parse(startDate);

        // Konverter starttider fra String til LocalTime, eller brug standardværdier
        LocalTime largeStart = (largeTheaterStartTime != null) ? LocalTime.parse(largeTheaterStartTime) : LocalTime.of(14, 0);
        LocalTime smallStart = (smallTheaterStartTime != null) ? LocalTime.parse(smallTheaterStartTime) : LocalTime.of(18, 0);

        // Opret forestillinger for hver dag i månederne frem
        LocalDate endDate = start.plusMonths(monthsAhead);
        for (LocalDate date = start; !date.isAfter(endDate); date = date.plusDays(1)) {
            for (Theater theater : theaters) {
                // Bestem starttidspunkt baseret på biografsalen
                LocalTime startTime = (theater.getName().equalsIgnoreCase("Stor Biografsal")) ? largeStart : smallStart;

                // Bestem sluttidspunktet baseret på filmens varighed
                LocalTime endTime = startTime.plusMinutes(movie.getDuration());

                // Opret og gem Showing-objektet
                Showing showing = new Showing();
                showing.setMovie(movie);
                showing.setTheater(theater);
                showing.setDate(date);
                showing.setStartTime(startTime);
                showing.setEndTime(endTime);
                showing.setMovieTitle(movie.getTitle());  // Sæt movieTitle

                showingRepository.save(showing);
            }
        }

        return ResponseEntity.ok("Showings generated successfully for movie: " + movie.getTitle());
    }


    @GetMapping("/date") // NY
    public List<Showing> getShowingsByDate(@RequestParam String date) {
        LocalDate showingDate = LocalDate.parse(date);
        return showingRepository.findByDate(showingDate);
    }


}