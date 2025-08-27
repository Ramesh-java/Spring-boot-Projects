package com.example.themleaf.service;

import com.example.themleaf.model.Task;
import com.example.themleaf.model.User;
import com.example.themleaf.repository.TASKREPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class Tservice {
    @Autowired
    private TASKREPO repo;

    public Task create(String description,User currentUser){
        Task task=new Task();
        task.setDescription(description);
        task.setStatus(false);
        task.setUser(currentUser);
        return repo.save(task);
    }

    public Task get(Long id){
        return repo.findById(id).orElse(null);
    }
    public List<Task> getAllTasks(User user){
        return repo.findByUser(user);
    }

    public void toggleTask(Long id) {

        Task task=repo.findById(id).orElse(null);
        if (task!=null){
            task.setStatus(!task.isStatus());
            repo.save(task);
        }
    }

    public void deleteTask(Long id){
        repo.deleteById(id);
    }
}
