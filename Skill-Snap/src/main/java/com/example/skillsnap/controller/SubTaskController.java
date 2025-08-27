package com.example.skillsnap.controller;


import com.example.skillsnap.dto.SubTaskDTO;
import com.example.skillsnap.model.Subtask;
import com.example.skillsnap.service.GoalService;
import com.example.skillsnap.service.SubtaskService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("*")
@RestController
@RequestMapping("/subtask")
public class SubTaskController {

    private final GoalService goalService;
    private final SubtaskService subtaskService;
    public SubTaskController(GoalService goalService, SubtaskService subtaskService) {
        this.goalService = goalService;
        this.subtaskService = subtaskService;
    }

    @PostMapping("/create")
    public ResponseEntity<?> createSubTask(@RequestBody SubTaskDTO subtask) {

        Subtask subtask1=subtaskService.create(subtask);
        return ResponseEntity.ok(subtask1);
    }
}
