package com.example.bookingsystem.Service.Train;

import com.example.bookingsystem.Model.Train.TrainTicket;
import com.example.bookingsystem.Model.User;
import com.example.bookingsystem.Repository.Train.TrainTicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainTicketService {
    @Autowired
    private TrainTicketRepository trainTicketRepository;

    public void create(TrainTicket ticket){
        trainTicketRepository.save(ticket);
    }

    public List<TrainTicket> findByUser(User currentuser) {
        return trainTicketRepository.findByUser(currentuser);
    }

    public void save(TrainTicket trainTicket) {
        trainTicketRepository.save(trainTicket);
    }
}
