package com.example.themleaf.repository;

import com.example.themleaf.model.Task;
import com.example.themleaf.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TASKREPO extends JpaRepository<Task,Long> {
    public List<Task> findByUser(User user);
}
