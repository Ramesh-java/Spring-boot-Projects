package com.example.bookingsystem.Model.Movie;

import com.example.bookingsystem.Model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MovieTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private ShowTime showTime;// show id to display the show the details of the movie
    @ManyToOne
    private Seating seating;
    private String  seats;// booked seats ,can be extracted by separating comma

    private double amount;//total amount
    private String seatRow;//row (a row,b row);
    @ManyToOne
    private User user; // to see which user booking the ticket

    public int getSeatCount(){
        return  (seats != null && !seats.isEmpty()) ? seats.split(",").length : 0;
    }

}