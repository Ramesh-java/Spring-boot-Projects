package com.example.bookingsystem.Service.Train;

import com.example.bookingsystem.Model.Train.Coach;
import com.example.bookingsystem.Model.Train.Train;
import com.example.bookingsystem.Repository.Train.CoachRepository;
import com.example.bookingsystem.Repository.Train.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoachService {

    @Autowired
    private CoachRepository coachRepository;
    @Autowired
    private TrainRepository trainRepository;

    public void deleteTickets(String coachName, Long trainId, int ticketCount, List<String> seatPreference){
        Coach coach = coachRepository.findCoachByNameAndTrainId(coachName,trainId);
        coach.setAvailability(coach.getAvailability()-ticketCount);
        for (String seatPreferences : seatPreference){
            switch (seatPreferences){
                case "upper" -> coach.setUpper(coach.getUpper() - ticketCount);
                case "lower" -> coach.setLower(coach.getLower() - ticketCount);
                case "middle" -> coach.setMiddle(coach.getMiddle() - ticketCount);
                case "sidelower" -> coach.setSideLower(coach.getSideLower() - ticketCount);
                case "sideupper" -> coach.setSideUpper(coach.getSideUpper() - ticketCount);
            }
        }
        Train train = trainRepository.findById(trainId).orElse(null);
        assert  train != null;
        train.setAvailability(train.getAvailability()-ticketCount);
        trainRepository.save(train);

    }

    public List<Coach> findByTrainId(Long trainId) {
        return coachRepository.findByTrainId(trainId);
    }

    public int findPriceByTrainIdAndName(Long trainId, String coachName) {
        return coachRepository.findPriceByTrainIdAndName(trainId,coachName);
    }

    public Coach findCoachByNameAndTrainId(String coachName, Long trainId) {
        return coachRepository.findCoachByNameAndTrainId(coachName, trainId);
    }
}
