package com.example.bookingsystem.Model.Movie;


import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Seating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int totalSeats;
    @ManyToOne
    private ShowTime showTime;
    private int availableSeats;
    private int price;
}
