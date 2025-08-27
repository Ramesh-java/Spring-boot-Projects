package com.example.themleaf;

import com.example.themleaf.model.Task;
import com.example.themleaf.model.User;
import com.example.themleaf.service.Tservice;
import com.example.themleaf.service.Uservice;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class Control {

    private final Uservice uservice;

    private final Tservice tservice;

    public Control(Uservice uservice,Tservice tservice){
        this.tservice=tservice;
        this.uservice=uservice;
    }


    @GetMapping("/html")
    public String getHome(Model model){
        model.addAttribute("greet","welcome");
        return "home";
    }
    @GetMapping("/loginPage")
    public String logEntry(){
        return "login";
    }
    @GetMapping("/signupPage")
    public String signEntry(){
        return "signup";
    }

    @PostMapping("/dashboard")
    public String login(@RequestParam("mobile")String number, @RequestParam("password")String password, Model model, HttpSession session){
        User user=uservice.ValidateUser(number,password);
        if (user!=null){
            session.setAttribute("user",user);
            List<Task>tasks=user.getTask();
            model.addAttribute("name",user.getName());
            model.addAttribute("tasks",tasks);
            return "dashboard";
        }
        return "login";
    }

    @PostMapping("/login")
    public String signUp(@RequestParam("name")String name,@RequestParam("mobile")String number,@RequestParam("password")String password,HttpSession session){
        session.setAttribute("user",uservice.create(name,password,number));
        return "redirect:/dashboard";
    }

    @PostMapping("/dashboard-task")
    public String newTask(@RequestParam("description")String description,Model model,HttpSession session){
        tservice.create(description,(User)session.getAttribute("user"));
        List<Task>tasks=tservice.getAllTasks((User)session.getAttribute("user"));
        model.addAttribute("name",((User)session.getAttribute("user")).getName());
        model.addAttribute("tasks",tasks);
        return "dashboard";
    }

    @GetMapping("/dashboard")
    public String returnDashboard(Model model,HttpSession session){
        List<Task>list=tservice.getAllTasks((User)session.getAttribute("user"));
        model.addAttribute("name",((User)session.getAttribute("user")).getName());
        model.addAttribute("tasks",list);
        return "dashboard";
    }

    @GetMapping("/{id}/toggle")
    public String toggleTask(@PathVariable Long id){
        tservice.toggleTask(id);
        return "redirect:/dashboard";
    }

    @GetMapping("/{id}/delete")
    public String deleteTask(@PathVariable Long id){
        tservice.deleteTask(id);
        return "redirect:/dashboard";
    }


}