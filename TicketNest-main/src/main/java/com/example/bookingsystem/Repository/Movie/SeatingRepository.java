package com.example.bookingsystem.Repository.Movie;

import com.example.bookingsystem.Model.Movie.Seating;
import com.example.bookingsystem.Model.Movie.ShowTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SeatingRepository extends JpaRepository<Seating,Long> {
    Seating findSeatingByShowTime(ShowTime showTime);
}
