package com.example.skillsnap.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.ToString;

@Entity
@Data
//@ToString(exclude = "password")
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Username cant be Empty")
    @Size(min = 3, max = 20, message = "Username length must be between 3 to 30")
    private String username;

    @NotBlank(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 3, message = "Password length should contain 3 characters atleast")
    private String password;


    /*public String toString(){
        return "name "+username+"\n"+"email "+email+"\n"+"password "+password+"\n";
    }*/

}
