package com.example.skillsnap.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDate;

@Entity
@Data
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Date should be present")
    private LocalDate date;


    private String notes;

    private int percentComplete;

    @ManyToOne
    private Goal goal;
}
