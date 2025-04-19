package com.example.bookingsystem.Repository.Train;

import com.example.bookingsystem.Model.Train.Coach;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CoachRepository extends JpaRepository<Coach, Long> {
    List<Coach> findByTrainId(Long trainId);

    Coach findCoachByNameAndTrainId(String name,Long id);

    @Query("SELECT c.price FROM Coach c WHERE c.trainId = :trainId AND c.name = :name")
    Integer findPriceByTrainIdAndName(@Param("trainId") Long trainId, @Param("name") String name);}
