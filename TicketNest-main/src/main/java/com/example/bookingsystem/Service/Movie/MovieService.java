package com.example.bookingsystem.Service.Movie;

import com.example.bookingsystem.Model.Movie.Movie;
import com.example.bookingsystem.Model.Movie.MovieTicket;
import com.example.bookingsystem.Model.User;
import com.example.bookingsystem.Repository.Movie.MovieRepository;
import com.example.bookingsystem.Repository.Movie.MovieTicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieTicketRepository movieTicketRepository;

    public List<Movie>getAllMovies(){
        return movieRepository.findAll();
    }

    public Movie getMovieById(Long id) {
        return movieRepository.findById(id).orElse(null);
    }

    public List<MovieTicket> findByUser(User currentuser) {
        return movieTicketRepository.findByUser(currentuser);
    }
}
