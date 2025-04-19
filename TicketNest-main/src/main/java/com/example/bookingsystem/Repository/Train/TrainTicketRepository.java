package com.example.bookingsystem.Repository.Train;

import com.example.bookingsystem.Model.Train.TrainTicket;
import com.example.bookingsystem.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainTicketRepository  extends JpaRepository<TrainTicket,Long> {

    List<TrainTicket> findByUser(User user);
}
