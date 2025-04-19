package com.example.bookingsystem.Controller;

import com.example.bookingsystem.Model.Bus.Bus;
import com.example.bookingsystem.Model.Bus.BusTicket;
import com.example.bookingsystem.Model.Order;
import com.example.bookingsystem.Model.Passenger;
import com.example.bookingsystem.Model.User;
import com.example.bookingsystem.Payment.PaymentResponse;
import com.example.bookingsystem.Payment.PaymentService;
import com.example.bookingsystem.Service.Bus.BusService;
import com.example.bookingsystem.Service.Bus.BusTicketService;
import com.example.bookingsystem.Service.OrderService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
@CrossOrigin(origins = "*")
@Controller
public class BusController {

    @Autowired
    private BusService busService;
    @Autowired
    private BusTicketService busTicketService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private OrderService orderService;
    //@Autowired
    //private UserController userController;



    @GetMapping("/bus")
    public String listOfBus(Model model){
        List<Bus> busList =  busService.returnBuses();
        model.addAttribute("List", busList);
        return "Bus";
    }
    @GetMapping("/seat/{id}")
    public String busSeats(@PathVariable Long id,Model model){
        model.addAttribute("id",id);
        List<Integer> availableSeats = busService.allTickets(busService.getBus(id));
        model.addAttribute("availableSeats",availableSeats);
        return "BusSeats";
    }


    @GetMapping("/search-bus")
    public String returnBus(@RequestParam("from") String from, @RequestParam("to") String to, @RequestParam("date") String date, Model model){
        List<Bus> busList =  busService.findBySourceAndDestinationAndDate(from,to,date);
        model.addAttribute("List", busList);
        return "Bus";
    }

    @GetMapping("/book")
    public String booking(@RequestParam("busId") Long busId, @RequestParam("seats") String seats, Model model){
        String[] arr=seats.split(",");
        model.addAttribute("seats", arr);
        model.addAttribute("busId", busId);
        //System.out.println(busId+ "seats"+seats);
        return "passenger";
    }


    @PostMapping("/submitPassengers")
    public ResponseEntity<String> passengers(@RequestBody List<Passenger> passengers, HttpSession session) throws IOException {
        List<Passenger> savedPassenger = busTicketService.saveAll(passengers);
        BusTicket busTicket = new BusTicket();
        //busTicket.setUser(userController.getCurrentuser());
        busTicket.setUser((User) session.getAttribute("user"));
        StringBuilder sb = new StringBuilder();
        for(int i=0; i<passengers.size(); i++){
            if (i!=passengers.size()-1){
                sb.append(passengers.get(i).getSeatId());
                sb.append(",");
            }else {
                sb.append(passengers.get(i).getSeatId());
            }
        }
        busTicket.setSeats(sb.toString());

        Bus bus = busService.findBusById(Long.valueOf(passengers.get(0).getBusId()));
        busTicket.setBus(bus);
        int count = passengers.size();
        busTicket.setTotalAmount(bus.getPrice()*count);
        busTicket.setPassengerList(savedPassenger);
        busTicketService.create(busTicket);
        List<Integer> availableSeats=busService.allTickets(busService.getBus(bus.getId()));
        bus.setAvailability(availableSeats.size());

        //Order order = orderService.createBusOrder(busTicket,userController.getCurrentuser());
        Order order = orderService.createBusOrder(busTicket,(User) session.getAttribute("user"));

        orderService.save(order);

        PaymentResponse response = null;
        try {
            response = paymentService.createPaymentLink(order);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        if(response != null && response.getPayment_url() != null)
            return  ResponseEntity.ok(response.getPayment_url());
        return ResponseEntity.badRequest().body("payment url not generated");
    }
}