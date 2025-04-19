package com.example.bookingsystem.Model.Bus;

import com.example.bookingsystem.Model.Passenger;
import com.example.bookingsystem.Model.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BusTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String seats;
    private int totalAmount;

    @ManyToOne
    private Bus bus;
    @ManyToOne
    private User user;
    @OneToMany
    private List<Passenger> passengerList;

    public int getSeatCount(){
        return  (seats != null && !seats.isEmpty()) ? seats.split(",").length : 0;
    }

    @Override
    public String toString() {
        return "BusTicket{" +
                "id=" + id +
                ", seats='" + seats + '\'' +
                ", totalAmount=" + totalAmount +
                ", bus=" + bus +
                ", user=" + user +
                ", passengerList=" + passengerList +
                '}';
    }
}