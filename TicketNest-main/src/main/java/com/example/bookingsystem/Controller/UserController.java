package com.example.bookingsystem.Controller;

import com.example.bookingsystem.Model.Bus.BusTicket;
import com.example.bookingsystem.Model.Movie.MovieTicket;
import com.example.bookingsystem.Model.Train.TrainTicket;
import com.example.bookingsystem.Model.User;
import com.example.bookingsystem.Service.Bus.BusTicketService;
import com.example.bookingsystem.Service.Movie.MovieService;
import com.example.bookingsystem.Service.Train.TrainTicketService;
import com.example.bookingsystem.Service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private BusTicketService busTicketService;
    @Autowired
    private MovieService movieService;
    @Autowired
    private TrainTicketService trainTicketService;
    @Autowired
    private JavaMailSender javaMailSender;
    //@Getter
    //private User currentuser ;

    @GetMapping("/")
    public String home(){
        return "Home";
    }

    @GetMapping("/dashboard")
    public String dashboard(Model model,HttpSession session){
        User user=(User) session.getAttribute("user");
        if (user == null) return "Login";
        model.addAttribute("name", user.getName().toUpperCase());
        return "Dashboard";
    }

    @GetMapping("/login")
    public String login(){
        return "Login";
    }
    @GetMapping("/signup")
    public String signup(){
        return "Signup";
    }
    
    @PostMapping("/sign")
    public String sign(@RequestParam("name") String name, @RequestParam("email") String email, @RequestParam("password") String password, Model model, HttpSession session){
        User user=userService.create(name,email,password);
        model.addAttribute("name", user.getName().toUpperCase());
        session.setAttribute("user",user);
        //currentuser = user;
        return "Dashboard";
    }

    @PostMapping("/log")
    public String log(@RequestParam("email") String email, @RequestParam("password") String password, Model model,HttpSession session){
        User user = userService.findByEmail(email,password);
        if (user != null){
            model.addAttribute("name",user.getName().toUpperCase());
            session.setAttribute("user",user);
            //currentuser = user;
            return "Dashboard";
        }
        model.addAttribute("error", "Invalid email or password");
        return "Login";
    }

    @GetMapping("/service")
    public String service(){
        return "service";
    }

    @GetMapping("/aboutout")
    public String aboutOut(){
        return "About-out";
    }

    @GetMapping("/aboutin")
    public String aboutIn(){
        return "About-in";
    }


    @GetMapping("/contact")
    public String contact(){
        return "Contact";
    }


    @PostMapping("/submit-contact")
    public String requestEmail(@RequestParam String name, @RequestParam String email, @RequestParam String subject, @RequestParam String message){
        SimpleMailMessage simpleMessage = new SimpleMailMessage();
        simpleMessage.setTo("rameshwarrior432@gmail.com");
        simpleMessage.setSubject(subject);
        simpleMessage.setText(name + "has sent you an email \n\n"+message);
        simpleMessage.setReplyTo(email);
        javaMailSender.send(simpleMessage);
        return "contact_success";
    }

    @GetMapping("/user-ticket")
    public String userTicket(Model model,HttpSession session){

        List<BusTicket> busTickets = busTicketService.findByUser((User)session.getAttribute("user"));
        List<MovieTicket> movieTickets = movieService.findByUser((User)session.getAttribute("user"));
        List<TrainTicket> trainTickets = trainTicketService.findByUser((User)session.getAttribute("user"));


        Map<Long, Integer> busSeatCounts = new HashMap<>();
        Map<Long, Integer> movieSeatCounts = new HashMap<>();

        for (BusTicket busTicket : busTickets) {
            busSeatCounts.put(busTicket.getId(), busTicket.getSeatCount());
        }
        for(MovieTicket movieTicket : movieTickets){
            movieSeatCounts.put(movieTicket.getId(), movieTicket.getSeatCount());
        }
        model.addAttribute("BusTicket", busTickets);
        model.addAttribute("TrainTicket", trainTickets);
        model.addAttribute("MovieTicket", movieTickets);
        model.addAttribute("busSeatCount",busSeatCounts);
        model.addAttribute("movieSeatCount", movieSeatCounts);
        return "UserTicket";
    }
}
