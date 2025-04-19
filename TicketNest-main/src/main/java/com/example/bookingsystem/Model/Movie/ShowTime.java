package com.example.bookingsystem.Model.Movie;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShowTime {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String theatre;
    @ManyToOne
    private Movie movie;
    private String time;
    private String date;

    @Override
    public String toString() {
        return "ShowTime{" +
                "id=" + id +
                ", theatre='" + theatre + '\'' +
                ", movie=" + movie +
                ", time='" + time + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
