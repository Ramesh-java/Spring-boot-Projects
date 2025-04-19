package com.example.bookingsystem.Repository.Movie;

import com.example.bookingsystem.Model.Movie.Movie;
import com.example.bookingsystem.Model.Movie.ShowTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ShowRepository extends JpaRepository<ShowTime, Long> {
    List<ShowTime> findShowTimesByMovie(Movie movie);

    ShowTime findShowTimeByDateAndTimeAndMovie(String date,String time,Movie movie);

//    @Query("SELECT s FROM ShowTime s WHERE s.movie.id = :movieId AND s.date = :date AND (s.time = :time OR s.time = REPLACE(:time, '.', ':'))")
//    ShowTime findShowTimeByMovieIdAndDateAndTime(@Param("movieId") Long movieId, @Param("date") String date, @Param("time") String time);

}
