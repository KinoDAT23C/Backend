package com.example.backend.service;


import com.example.backend.model.Showing;
import com.example.backend.repository.ShowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ShowingService {

    @Autowired
    private ShowingRepository showingRepository;

    public Showing createShowing(Showing showing) {
        return showingRepository.save(showing);
    }

    public List<Showing> getAllShowings() {
        return showingRepository.findAll();
    }

    public List<Showing> getShowingsForNextThreeMonths() {
        LocalDate today = LocalDate.now();
        LocalDate threeMonthsLater = today.plusMonths(3);
        return showingRepository.findByDateBetween(today, threeMonthsLater);
    }

    public List<Showing> getShowingsByTheater(Long theaterId) {
        return showingRepository.findByTheater_TheaterId(theaterId);
    }

    // Update an existing showing
    /*public Showing updateShowing(Long showingId, Showing updatedShowing) {
        return showingRepository.findById(showingId).map(showing -> {
            showing.setMovieTitle(updatedShowing.getMovieTitle());
            showing.setDate(updatedShowing.getDate());
            showing.setStartTime(updatedShowing.getStartTime());
            showing.setEndTime(updatedShowing.getEndTime());
            showing.setTheater(updatedShowing.getTheater());
            return showingRepository.save(showing);
        }).orElseThrow(() -> new ResourceNotFoundException("Showing not found with id " + showingId));
    }*/

    // Delete a showing
    public void deleteShowing(Long showingId) {
        showingRepository.deleteById(showingId);
    }}
