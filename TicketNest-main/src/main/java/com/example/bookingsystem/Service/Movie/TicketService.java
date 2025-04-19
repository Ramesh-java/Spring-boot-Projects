package com.example.bookingsystem.Service.Movie;

import com.example.bookingsystem.Model.Movie.Seating;
import com.example.bookingsystem.Model.Movie.ShowTime;
import com.example.bookingsystem.Model.Movie.MovieTicket;
import com.example.bookingsystem.Repository.Movie.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public List<MovieTicket> getAllTicketsByShowAndSeat(ShowTime show, Seating seating){
        return ticketRepository.findMovieTicketsByShowTimeAndSeating(show,seating);
    }

    public List<Integer> getAvailability(List<MovieTicket> allTickets){
        List<Integer>availableSeats=new ArrayList<>();
        for (int i=1;i<=20;i++){
            if (!booked(i,allTickets)){
                availableSeats.add(i);
            }
        }

        return availableSeats;
    }

    private boolean booked(int i,List<MovieTicket>tickets) {
        for (MovieTicket ticket:tickets){
            String[] seats=ticket.getSeats().split(",");
            List<String>seats2= Arrays.asList(seats);
            if (seats2.contains(String.valueOf(i))){
                return true;
            }
        }
        return false;
    }

    public void create(MovieTicket ticketDetails) {
        ticketRepository.save(ticketDetails);
    }
}
