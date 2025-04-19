package com.example.bookingsystem.Controller;


import com.example.bookingsystem.Model.Train.Train;
import com.example.bookingsystem.Service.Bus.BusService;
import com.example.bookingsystem.Service.Train.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ticket")
public class TicketController {

    @Autowired
    private BusService busService;
    @Autowired
    private TrainService trainService;

    @GetMapping("/seats/{id}")
    public ResponseEntity<List<Integer>> busAvailability(@PathVariable int id){
        List<Integer> availableList = busService.allTickets(busService.getBus(Long.parseLong(String.valueOf(id))));
        return ResponseEntity.ok(availableList);
    }

    @GetMapping("/train/{trainId}")
    public ResponseEntity<Train> getTrainById(@PathVariable long trainId){
        Train train = trainService.getTrain(trainId);
        return ResponseEntity.ok(train);
    }


    @GetMapping("/coachPage")
    public String coach(@RequestParam int tickets,Model model){
        model.addAttribute("count",tickets);
        return "TrainCoach";
    }


}