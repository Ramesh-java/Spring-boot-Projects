package com.example.skillsnap.repository;

import com.example.skillsnap.model.Customer;
import com.example.skillsnap.model.Goal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GoalRepo extends JpaRepository<Goal,Long> {
    List<Goal> findGoalsByCustomer(Customer customer);
}
