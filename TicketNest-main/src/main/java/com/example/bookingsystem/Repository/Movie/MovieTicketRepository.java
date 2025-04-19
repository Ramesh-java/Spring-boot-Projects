package com.example.bookingsystem.Repository.Movie;

import com.example.bookingsystem.Model.Movie.MovieTicket;
import com.example.bookingsystem.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieTicketRepository extends JpaRepository<MovieTicket,Long> {

    List<MovieTicket> findByUser(User user);
}
