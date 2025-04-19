package com.example.bookingsystem.Model.Train;

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
public class Coach {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long trainId;
    private String name;
    private int upper;
    private int lower;
    private int middle;
    private int sideLower;
    private int sideUpper;
    private int total_seats;
    private int availability;
    private int price;

    @Override
    public String toString() {
        return "Coach{" +
                "id=" + id +
                ", trainId=" + trainId +
                ", name='" + name + '\'' +
                ", upper=" + upper +
                ", lower=" + lower +
                ", middle=" + middle +
                ", sideLower=" + sideLower +
                ", sideUpper=" + sideUpper +
                ", total_seats=" + total_seats +
                ", availability=" + availability +
                ", price=" + price +
                '}';
    }
}
