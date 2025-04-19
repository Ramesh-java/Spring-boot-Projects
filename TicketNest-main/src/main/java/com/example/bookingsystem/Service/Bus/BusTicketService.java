package com.example.bookingsystem.Service.Bus;

import com.example.bookingsystem.Model.Bus.BusTicket;
import com.example.bookingsystem.Model.Passenger;
import com.example.bookingsystem.Model.User;
import com.example.bookingsystem.Repository.Bus.BusTicketRepository;
import com.example.bookingsystem.Repository.PassengerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BusTicketService {

    @Autowired
    private BusTicketRepository busTicketRepository;
    @Autowired
    private PassengerRepository passengerRepository;

    public void create(BusTicket busTicket){
        busTicketRepository.save(busTicket);
    }

    public List<BusTicket> findByUser(User currentuser) {
        return busTicketRepository.findByUser(currentuser);
    }

    public List<Passenger> saveAll(List<Passenger> passengers) {
        return passengerRepository.saveAll(passengers);
    }
}
