package com.example.bookingsystem.Repository.Train;

import com.example.bookingsystem.Model.Train.Train;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainRepository extends JpaRepository<Train,Long> {
    List<Train> findTrainBySourceAndDestinationAndDate(String source,String destination,String date);

}
