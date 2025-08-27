package com.example.skillsnap.service;

import com.example.skillsnap.dto.GoalDTO;
import com.example.skillsnap.dto.newGoalDTO;
import com.example.skillsnap.model.Customer;
import com.example.skillsnap.model.Goal;
import com.example.skillsnap.model.Subtask;
import com.example.skillsnap.repository.GoalRepo;
import com.example.skillsnap.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class GoalService {

    private final GoalRepo goalRepo;
    private final UserService userService;

    public GoalService (GoalRepo goalRepo, UserService userService) {
        this.goalRepo=goalRepo;
        this.userService=userService;
    }

    @Transactional
    public Goal create(newGoalDTO goal){
        Goal goal1=new Goal();
        goal1.setTitle(goal.getTitle());
        goal1.setTargetDate(dateChange(goal.getDate()));
        goal1.setCategory(goal.getCategory());
        goal1.setCustomer(userService.get(goal.getCustomerId()));
        return goalRepo.save(goal1);
    }
    public List<GoalDTO> getGoals(Customer customer){
        List<Goal>list=goalRepo.findGoalsByCustomer(customer);
        List<GoalDTO>data=new ArrayList<>();
        for (Goal goal:list){
            data.add(GoalDTO.from(goal));
        }
        return data;
    }


    public Goal getGoalById(int id) {
        System.out.println(id);
        System.out.println(goalRepo.findById(Long.parseLong(String.valueOf(id))).orElse(null));
        return goalRepo.findById(Long.parseLong(String.valueOf(id))).orElse(null);

    }

    @Transactional
    public Goal delete(int id) {
        Goal goal=goalRepo.findById(Long.parseLong(String.valueOf(id))).orElse(null);
        goalRepo.deleteById(Long.parseLong(String.valueOf(id)));
        return  goal;
    }

    public static int getProgressPercentage(Goal goal) {

        int completedCount=(int)goal.getSubtasks().stream().filter(Subtask::isCompleted).count();
        int totalTasks=goal.getSubtasks().size();

        if (completedCount!=0||totalTasks!=0)return (completedCount*100)/totalTasks;
        else {
            return 0;
        }

    }


    public LocalDate dateChange(String date){
        DateTimeFormatter formatter= DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return LocalDate.parse(date,formatter);

    }
}
