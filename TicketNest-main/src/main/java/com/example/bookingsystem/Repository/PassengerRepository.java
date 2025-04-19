package com.example.bookingsystem.Repository;

import com.example.bookingsystem.Model.Passenger;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassengerRepository extends JpaRepository<Passenger,Long> {
}
