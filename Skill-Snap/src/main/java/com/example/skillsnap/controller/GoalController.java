package com.example.skillsnap.controller;


import com.example.skillsnap.dto.newGoalDTO;
import com.example.skillsnap.model.Goal;
import com.example.skillsnap.model.Subtask;
import com.example.skillsnap.service.GoalService;
import com.example.skillsnap.service.SubtaskService;
import com.example.skillsnap.service.UserService;
import org.apache.catalina.LifecycleState;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin("*")
@RestController
@RequestMapping("/goal")
public class GoalController {

    private final GoalService goalService;
    private final UserService userService;
    private final SubtaskService subtaskService;

    public GoalController(GoalService goalService, UserService userService, SubtaskService subtaskService) {
        this.goalService = goalService;
        this.userService = userService;
        this.subtaskService = subtaskService;
    }
    @PostMapping("/create")
    public ResponseEntity<?> createGoal(@RequestBody newGoalDTO goal) {

        Goal saved=goalService.create(goal);

        return ResponseEntity.ok(saved);
    }



    /*@PutMapping("/editGoal/{id}")
    public ResponseEntity<?>editGoal(@PathVariable int id,@RequestBody Goal goal) {
        Goal current=goalService.getGoalById(id);
        current.setTitle(goal.getTitle());
        current.setCategory(goal.getCategory());
        current.setTargetDate(goal.getTargetDate());
        goalService.create(current);

        return ResponseEntity.ok(current);

    }*/

    @GetMapping("/getGoal/{id}")
    public ResponseEntity<?> getGoalById(@PathVariable("id") int id) {

        return ResponseEntity.ok(goalService.getGoalById(id));

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteGoalById(@PathVariable("id") int id) {
        Goal goal=goalService.delete(id);
        return ResponseEntity.ok(goal);
    }

    @PutMapping("/{subtaskId}/toggle")
    public ResponseEntity<?> toggleGoal(@PathVariable int subtaskId) {
        subtaskService.toggle(subtaskId);
        return ResponseEntity.ok().build();
    }


}
