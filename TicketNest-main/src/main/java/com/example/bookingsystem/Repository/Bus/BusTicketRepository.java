package com.example.bookingsystem.Repository.Bus;

import com.example.bookingsystem.Model.Bus.Bus;
import com.example.bookingsystem.Model.Bus.BusTicket;
import com.example.bookingsystem.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BusTicketRepository extends JpaRepository<BusTicket,Long> {

    List<BusTicket> findAllByBus(Bus bus);

    Long findBusTicketByUser(User user);

    List<BusTicket> findByUser(User user);
}
