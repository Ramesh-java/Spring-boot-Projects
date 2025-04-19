package com.example.bookingsystem.Repository.Bus;

import com.example.bookingsystem.Model.Bus.Bus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusRepository extends JpaRepository<Bus,Long> {
    List<Bus> findBusBySourceAndDestinationAndDate(String source, String destination,String date);
    Bus findBusById(Long busId);
}