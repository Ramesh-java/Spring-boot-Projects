package com.example.bookingsystem.Service.Movie;

import com.example.bookingsystem.Model.Movie.Movie;
import com.example.bookingsystem.Model.Movie.ShowTime;
import com.example.bookingsystem.Repository.Movie.MovieRepository;
import com.example.bookingsystem.Repository.Movie.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ShowTimeService {
    @Autowired
    private ShowRepository showRepository;
    @Autowired
    private MovieRepository movieRepository;

    public List<ShowTime> getShowsByMovie(Movie movie){
        return showRepository.findShowTimesByMovie(movie);
    }


    public ShowTime getShowByMovieAndShow(String date,String time, String movie) {
        Movie current=movieRepository.findMovieByName(movie);
        System.out.println();
        System.out.println(current);
        return showRepository.findShowTimeByDateAndTimeAndMovie(date,time,current);
    }

    public ShowTime getShow(String movieName, String showDate, String showTime) {
        String name = movieName.replace(" ", "");
        String date = showDate.replace(" ", "");
        String time = convertTo24HourFormat(showTime); // Convert time to 24-hour format

        System.out.println();
        System.out.println("get show method " + name + " " + time + " " + date);
        return getShowByMovieAndShow(date, time, name);
    }

    private String convertTo24HourFormat(String time) {
        try {

            SimpleDateFormat inputFormat = new SimpleDateFormat("hh:mm a"); // 12-hour format
            SimpleDateFormat outputFormat = new SimpleDateFormat("HH.mm");  // 24-hour format with .
            Date date = inputFormat.parse(time);
            return outputFormat.format(date);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return time;
        }
    }


}
