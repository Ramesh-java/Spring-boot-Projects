package com.example.bookingsystem.Controller;

import com.example.bookingsystem.Model.*;
import com.example.bookingsystem.Model.Train.Coach;
import com.example.bookingsystem.Model.Train.Train;
import com.example.bookingsystem.Model.Train.TrainTicket;
import com.example.bookingsystem.Payment.PaymentResponse;
import com.example.bookingsystem.Payment.PaymentService;
import com.example.bookingsystem.Repository.Train.TrainRepository;
import com.example.bookingsystem.Service.OrderService;
import com.example.bookingsystem.Service.Train.CoachService;
import com.example.bookingsystem.Service.Train.TrainService;
import com.example.bookingsystem.Service.Train.TrainTicketService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@Controller
public class TrainController {

    @Autowired
    private TrainService trainService;

    @Autowired
    private CoachService coachService;
    @Autowired
    private TrainRepository trainRepository;
    @Autowired
    private OrderService orderService;
    @Autowired
    private PaymentService paymentService;
    //@Autowired
    //private UserController userController;
    @Autowired
    private TrainTicketService trainTicketService;

    @GetMapping("/train")
    public String listOfTrain(Model model){
        List<Train> trainList = trainService.returnTrain();
        model.addAttribute("List",trainList);
        return "Train";
    }

    @GetMapping("/search-train")
    public String returnTrain(@RequestParam("from") String from,@RequestParam("to")String to, @RequestParam("date")String date, Model model){
        List<Train> trainList = trainService.searchTrain(from,to,date);
        model.addAttribute("List",trainList);
        return "Train";
    }

    @GetMapping("/train-ticket/{trainId}")
    public String trainTicket(@PathVariable Long trainId, Model model){
        List<Coach> coachList = coachService.findByTrainId(trainId);
        model.addAttribute("coaches",coachList);
        model.addAttribute("train",trainId);
        return "TrainCoach";
    }

    @GetMapping("/bookTicket")
    public String bookTrainTicket(@RequestParam Long trainId, @RequestParam String coachName, Model model) {
        Train train = trainRepository.findById(trainId).orElse(null);
        int price = coachService.findPriceByTrainIdAndName(trainId,coachName);
        model.addAttribute("price", price);
        model.addAttribute("train", train);
        model.addAttribute("trainId", trainId);
        model.addAttribute("coachName", coachName);
        return "TrainTicket";
    }
    @GetMapping("/coach-page/{trainId}/{coachName}")
    public String bookTrain(@PathVariable Long trainId, @PathVariable String coachName, @RequestParam("tickets") int ticketCount, Model model){
        model.addAttribute("count", ticketCount);
        model.addAttribute("trainId", trainId);
        model.addAttribute("coachName", coachName);
        return "TrainConfirm";
    }

    @PostMapping("/submitTrainPassengers/{trainId}/{coachName}/{ticketCount}")
    public ResponseEntity<String> submitTrainPassenger(@PathVariable Long trainId, @PathVariable String coachName, @PathVariable int ticketCount, @RequestBody Map<String, List<Map<String, String>>> requestBody, HttpSession session) {

        List<Map<String, String>> passengers = requestBody.get("passengers");

        TrainTicket trainTicket = new TrainTicket();
        //trainTicket.setUser(userController.getCurrentuser());
        trainTicket.setUser((User) session.getAttribute("user"));

        trainTicket.setTrain(trainRepository.findById(trainId).orElse(null));
        trainTicket.setCoach(coachService.findCoachByNameAndTrainId(coachName,trainId));
        trainTicket.setTicketCount(ticketCount);
        trainTicketService.save(trainTicket);

        int amount = coachService.findCoachByNameAndTrainId(coachName,trainId).getPrice();
        trainTicket.getCoach().setPrice(amount*ticketCount);

        //Order order = orderService.createTrainOrder(trainTicket,userController.getCurrentuser());
        Order order = orderService.createTrainOrder(trainTicket,(User) session.getAttribute("user"));

        orderService.save(order);

        // Extract seat preferences and process them
        List<String> seatPreferences = passengers.stream()
            .map(p -> p.get("seatPreference"))
                .collect(Collectors.toList());
        coachService.deleteTickets(coachName,trainId,ticketCount,seatPreferences);

        PaymentResponse response = null;
        try {
            response = paymentService.createPaymentLink(order);
        }catch (Exception e){
            e.printStackTrace();
        }

        if(response != null && response.getPayment_url() != null)
            return ResponseEntity.ok("{\"paymentUrl\": \"" + response.getPayment_url() + "\"}");
        return ResponseEntity.badRequest().body("payment url not generated");
    }

}
