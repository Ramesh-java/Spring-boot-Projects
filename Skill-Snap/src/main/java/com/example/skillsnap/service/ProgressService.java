package com.example.skillsnap.service;

import com.example.skillsnap.model.Goal;
import com.example.skillsnap.model.Progress;
import com.example.skillsnap.repository.ProgressRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProgressService {
    private final ProgressRepo progressRepo;

    public ProgressService(ProgressRepo progressRepo){
        this.progressRepo=progressRepo;
    }

    @Transactional
    public void create(Progress progress){
        progressRepo.save(progress);
    }

    public Progress getProgress(Goal goal){
        Optional<Progress> list=progressRepo.findByGoal(goal);
        return list.isPresent()?list.stream().toList().get(0):null;
    }


}