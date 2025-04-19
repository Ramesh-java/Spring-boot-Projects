package com.example.bookingsystem.Service.Movie;


import com.example.bookingsystem.Model.Movie.Seating;
import com.example.bookingsystem.Model.Movie.ShowTime;

import com.example.bookingsystem.Repository.Movie.SeatingRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeatingService {
    @Autowired
    private SeatingRepository seatingRepository;

    public Seating getSeatingByShow(ShowTime showTime) {
        return seatingRepository.findSeatingByShowTime(showTime);
    }

    public void update(Seating seating) {
        seatingRepository.save(seating); // ✅ This saves the updated seat count
    }
}

