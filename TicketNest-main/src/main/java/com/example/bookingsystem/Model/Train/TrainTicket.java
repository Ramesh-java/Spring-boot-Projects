package com.example.bookingsystem.Model.Train;

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
public class TrainTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int ticketCount;

    @ManyToOne
    private Train train;
    @ManyToOne
    private User user;
    @ManyToOne
    private Coach coach;

    @Override
    public String toString() {
        return "TrainTicket{" +
                "id=" + id +
                ", ticketCount=" + ticketCount +
                ", train=" + train +
                ", user=" + user +
                ", coach=" + coach +
                '}';
    }
}