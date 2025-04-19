package com.example.bookingsystem.Model;


import com.example.bookingsystem.Model.Bus.BusTicket;
import com.example.bookingsystem.Model.Movie.MovieTicket;
import com.example.bookingsystem.Model.Train.TrainTicket;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Order_products")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    private MovieTicket ticket;
    @ManyToOne
    private BusTicket busTicket;
    @ManyToOne
    private TrainTicket trainTicket;
    private Long userId;
    private String bookingType;
    private double totalAmount;
}
