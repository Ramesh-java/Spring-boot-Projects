package com.example.bookingsystem.Repository.Movie;

import com.example.bookingsystem.Model.Movie.Seating;
import com.example.bookingsystem.Model.Movie.ShowTime;
import com.example.bookingsystem.Model.Movie.MovieTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<MovieTicket, Long> {

    List<MovieTicket> findMovieTicketsByShowTimeAndSeating(ShowTime showTime, Seating seating);
}
