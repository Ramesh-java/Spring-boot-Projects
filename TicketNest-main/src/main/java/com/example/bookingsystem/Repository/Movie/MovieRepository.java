package com.example.bookingsystem.Repository.Movie;

import com.example.bookingsystem.Model.Movie.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<Movie,Long> {
    Movie findMovieByName(String name);
    List<Movie> findAllByName(String name);

}
