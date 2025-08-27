package com.example.auctionbot.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Player {
    @Id
    private String name;

    private int price;
    @ManyToOne
    private Team team;
}
