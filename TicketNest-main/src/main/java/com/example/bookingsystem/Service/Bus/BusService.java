package com.example.bookingsystem.Service.Bus;

import com.example.bookingsystem.Model.Bus.Bus;
import com.example.bookingsystem.Model.Bus.BusTicket;
import com.example.bookingsystem.Model.User;
import com.example.bookingsystem.Repository.Bus.BusRepository;
import com.example.bookingsystem.Repository.Bus.BusTicketRepository;
import com.example.bookingsystem.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class BusService {

    @Autowired
    private BusRepository busRepository;

    @Autowired
    private BusTicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Bus> returnBuses(){
        return busRepository.findAll();
    }

    public List<Bus> findBySourceAndDestinationAndDate(String source, String destination, String date){
        return busRepository.findBusBySourceAndDestinationAndDate(source,destination, date);
    }

    public Bus getBus(Long id){
       return busRepository.findById(id).orElse(null);
    }

    public int getCapacity(Bus bus){
        return bus.getAvailability();
    }

    public List<Integer> allTickets(Bus bus){
        int id = 25;
        List<Integer> availableTickets = new ArrayList<>();
        List<BusTicket> tickets = ticketRepository.findAllByBus(bus);
        for (int i=1; i<=id; i++){
            if (!isBooked(tickets,i)){
                availableTickets.add(i);
            }
        }
        return availableTickets;
    }

    public boolean isBooked(List<BusTicket> busTickets, int seatNumber){
        for (BusTicket ticket:busTickets){
            String[] seats=ticket.getSeats().split(",");
            List<String>list= Arrays.asList(seats);
            if(list.contains(String.valueOf(seatNumber))){
                return true;
            }
        }return false;
    }

    public Long findBusByUserId(Long userId){
        User user=userRepository.findById(userId).orElse(null);
        return ticketRepository.findBusTicketByUser(user);
    }

    public Bus findBusById(Long aLong) {
        return busRepository.findBusById(aLong);
    }
}
