package com.example.skillsnap.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Fetch;

@Entity
@Data
@ToString(exclude = "goal")
public class Subtask {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "task name cant be empty")
    @Size(min = 3, message = "task name should contain minimum 3 characters")
    private String taskName;


    private boolean completed;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    private Goal goal;


}
