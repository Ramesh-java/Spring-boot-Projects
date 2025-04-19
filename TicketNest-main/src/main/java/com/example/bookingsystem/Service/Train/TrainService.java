package com.example.bookingsystem.Service.Train;

import com.example.bookingsystem.Model.Train.Train;
import com.example.bookingsystem.Repository.Train.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrainService {

    @Autowired
    TrainRepository trainRepository;

    public List<Train> returnTrain(){
        return trainRepository.findAll();
    }

    public List<Train> searchTrain(String source, String destination, String date){
        return trainRepository.findTrainBySourceAndDestinationAndDate(source, destination, date);
    }

    public Train getTrain(Long trainId){
        return trainRepository.findById(trainId).orElse(null);
    }
}
