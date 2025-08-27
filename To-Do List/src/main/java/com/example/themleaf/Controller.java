package com.example.themleaf;

import com.example.themleaf.model.User;
import com.example.themleaf.service.Uservice;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class Controller {
    private final Uservice uservice;
    public Controller(Uservice uservice){
        this.uservice=uservice;
    }
    @PostMapping("/create")
    public User create(@RequestParam String name, @RequestParam String number, @RequestParam String password){
        return uservice.create(name,password,number);
    }

    @GetMapping("/get")
    public List<User> getAll(){
        return uservice.getAll();
    }
}
