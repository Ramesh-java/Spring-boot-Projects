package com.example.bookingsystem.Payment;

import com.example.bookingsystem.Model.Bus.BusTicket;
import com.example.bookingsystem.Model.Movie.MovieTicket;
import com.example.bookingsystem.Model.Order;
import com.example.bookingsystem.Model.Train.TrainTicket;
import com.example.bookingsystem.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class PaymentController {


    private final OrderService orderService;
    public PaymentController(OrderService orderService){
        this.orderService=orderService;
    }

    @GetMapping("/success")
    public String successPayment(@RequestParam Long order_id, Model model){
        System.out.println(order_id);
        Order order=orderService.getOrder(order_id);
        String orderType = order.getBookingType();
        System.out.println(orderType);
        switch (orderType){

            case "Movie" -> {

                MovieTicket ticketDetails = order.getTicket();
                String nameOfTheMovie = ticketDetails.getShowTime().getMovie().getName();
                String dateOfShow=ticketDetails.getShowTime().getDate();
                String timeOfShow=ticketDetails.getShowTime().getTime();
                String[] bookedSeatList=ticketDetails.getSeats().split(",");//this is list of seat number as a string array
                double paidAmount=ticketDetails.getAmount();
                String paymentStatus="success";

                model.addAttribute("nameOfTheMovie",nameOfTheMovie);
                model.addAttribute("dateOfShow",dateOfShow);
                model.addAttribute("timeOfShow",timeOfShow);
                model.addAttribute("bookedSeatList",bookedSeatList);
                model.addAttribute("paidAmount",paidAmount);
                model.addAttribute("paymentStatus",paymentStatus);
                model.addAttribute("BookedSeats",ticketDetails.getSeats());
                model.addAttribute("seats",bookedSeatList.length);
            }
            case "Bus" -> {

                BusTicket busTicket = order.getBusTicket();
                System.out.println(busTicket);
                String busName = busTicket.getBus().getName();
                String source = busTicket.getBus().getSource();
                String destination = busTicket.getBus().getDestination();
                String departureTime = busTicket.getBus().getDepartureTime();
                String date = busTicket.getBus().getDate();
                String[] arr= busTicket.getSeats().split(",");
                int seats = arr.length;

                model.addAttribute("BusName", busName);
                model.addAttribute("BusSource", source);
                model.addAttribute("BusDestination", destination);
                model.addAttribute("BusDeparture", departureTime);
                model.addAttribute("BusDate", date);
                model.addAttribute("TotalSeats", seats);
                model.addAttribute("BusSeats", busTicket.getSeats());
            }
            case "Train" -> {
                TrainTicket trainTicket = order.getTrainTicket();
                String trainName = trainTicket.getTrain().getName();
                String source = trainTicket.getTrain().getSource();
                String destination = trainTicket.getTrain().getDestination();
                String departureTime = trainTicket.getTrain().getDepartureTime();
                String date = trainTicket.getTrain().getDate();
                int seats = trainTicket.getTicketCount();
                String coach = trainTicket.getCoach().getName();

                model.addAttribute("TrainName", trainName);
                model.addAttribute("TrainSource", source);
                model.addAttribute("TrainDestination", destination);
                model.addAttribute("TrainDeparture", departureTime);
                model.addAttribute("TrainDate", date);
                model.addAttribute("TrainSeat", seats);
                model.addAttribute("CoachName", coach);
            }
        }

        // modify ->
        return "orderSuccessPage";
    }

    @GetMapping("/failed")
    public String failedPayment(){
        return "Dashboard";
    }
}
