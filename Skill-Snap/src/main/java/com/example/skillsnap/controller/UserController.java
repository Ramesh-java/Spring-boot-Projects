package com.example.skillsnap.controller;

import com.example.skillsnap.dto.GoalDTO;
import com.example.skillsnap.model.Customer;
import com.example.skillsnap.model.Goal;
import com.example.skillsnap.service.GoalService;
import com.example.skillsnap.service.UserService;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;
    private final GoalService goalService;

    public UserController(UserService service,GoalService goalService) {
        this.userService=service;
        this.goalService=goalService;
    }

    @GetMapping("/get")
    public String get(){
        System.out.println("working");
        return "Working";
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@RequestBody Customer customer/*, BindingResult result, HttpSession session*/){
        /*if (result.hasErrors()){
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }

        session.setAttribute("customer",customer);*/
        userService.create(customer);
        System.out.println(customer);

        return ResponseEntity.ok(customer);
    }

    @GetMapping("/login")
    public ResponseEntity<?>login
            (@RequestParam String email, @RequestParam String password){
    if (userService.validateUser(email,password)){
        return ResponseEntity.ok("Login Successful");
    }
    return ResponseEntity.badRequest().body("Login Failed");
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getGoalsById(@PathVariable("id") int id)   {
        System.out.println(id);
        List<GoalDTO> list=goalService.getGoals(userService.getCustomer(id));
        //System.out.println(userService.getCustomer(id));
        //System.out.println(list);
        return ResponseEntity.ok(list);
    }
}
