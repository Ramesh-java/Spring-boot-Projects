package com.example.bookingsystem.Model.Train;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Train {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String arrivalTime;
    private String departureTime;
    private String source;
    private String destination;
    private String date;
    private int availability;
    private int price;

    private int two_Ac =30;
    private int three_Ac=30;
    private int sleeper=30;
    private int general=30;
}