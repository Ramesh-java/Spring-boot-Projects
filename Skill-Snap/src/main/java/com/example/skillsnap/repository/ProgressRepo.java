package com.example.skillsnap.repository;

import com.example.skillsnap.model.Goal;
import com.example.skillsnap.model.Progress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProgressRepo extends JpaRepository<Progress,Long> {

Optional<Progress> findByGoal(Goal goal);
}
