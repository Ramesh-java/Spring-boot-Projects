package com.example.skillsnap.service;

import com.example.skillsnap.dto.SubTaskDTO;
import com.example.skillsnap.model.Goal;
import com.example.skillsnap.model.Subtask;
import com.example.skillsnap.repository.GoalRepo;
import com.example.skillsnap.repository.SubtaskRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SubtaskService {
    private final SubtaskRepo subtaskRepo;
    private final GoalService goalService;

    public SubtaskService(SubtaskRepo subtaskRepo, GoalService goalService) {
        this.subtaskRepo = subtaskRepo;
        this.goalService = goalService;
    }

    @Transactional
    public void create(Subtask subtask){

        subtaskRepo.save(subtask);
    }

    public List<Subtask> getByGoal
            (Goal goal){ ///returning all subtasks of a specific goal
        Optional<Subtask>tasks=subtaskRepo.findSubtaskByGoal(goal);

        if (tasks.isPresent()){
            return new ArrayList<>(tasks.stream().toList());
        }
        return null;
    }

    public  Subtask getSubTask(int id){
        return subtaskRepo.findById(Long.parseLong(String.valueOf(id))).orElse(null);
    }

    @Transactional
    public void toggle(int subtaskId) {
        Subtask subtask=getSubTask(subtaskId);
        boolean status= subtask.isCompleted();
        subtask.setCompleted(!status);

        subtaskRepo.save(subtask);
    }

    @Transactional
    public Subtask create(SubTaskDTO subTaskDTO){
        Subtask subtask=new Subtask();
        Goal goal=goalService.getGoalById(subTaskDTO.getGoalId());
        subtask.setGoal(goal);
        subtask.setTaskName(subTaskDTO.getTaskName());
        return subtaskRepo.save(subtask);
    }
}

