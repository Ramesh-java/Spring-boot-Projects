package com.example.skillsnap.dto;

import com.example.skillsnap.model.Goal;
import com.example.skillsnap.model.Subtask;
import com.example.skillsnap.service.GoalService;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;
@Data
public class GoalDTO {
    private Long id;
    private String title;
    private String category;
    private LocalDate date;
    private List<Subtask>subtasks;
    private int progressPercent;

    public static GoalDTO from(Goal goal) {
        GoalDTO dto = new GoalDTO();
        dto.setId(goal.getId());
        dto.setTitle(goal.getTitle());
        dto.setCategory(goal.getCategory());
        dto.setDate(goal.getTargetDate());
        dto.setSubtasks(goal.getSubtasks());

        int calculatedPercentage= GoalService.getProgressPercentage(goal);
        dto.setProgressPercent(calculatedPercentage);
        return dto;

    }
}
