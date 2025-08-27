package com.example.skillsnap.repository;

import com.example.skillsnap.model.Goal;
import com.example.skillsnap.model.Subtask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubtaskRepo extends JpaRepository<Subtask,Long> {
Optional<Subtask> findSubtaskByGoal(Goal goal);
}
