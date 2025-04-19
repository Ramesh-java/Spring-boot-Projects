package com.example.bookingsystem.Controller;

import com.example.bookingsystem.Model.Order;
import com.example.bookingsystem.Model.Movie.Movie;
import com.example.bookingsystem.Model.Movie.Seating;
import com.example.bookingsystem.Model.Movie.ShowTime;
import com.example.bookingsystem.Model.Movie.MovieTicket;
import com.example.bookingsystem.Model.User;
import com.example.bookingsystem.Service.OrderService;
import com.example.bookingsystem.Service.Movie.MovieService;
import com.example.bookingsystem.Service.Movie.SeatingService;
import com.example.bookingsystem.Service.Movie.ShowTimeService;
import com.example.bookingsystem.Service.Movie.TicketService;
import com.example.bookingsystem.Payment.PaymentResponse;
import com.example.bookingsystem.Payment.PaymentService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Controller
public class MovieController {

    //@Autowired
    //private UserController userController;
    @Autowired
    private MovieService movieService;
    @Autowired
    private ShowTimeService showTimeService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private SeatingService seatingService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private PaymentService paymentService;

    @GetMapping("/movie")
    public String movieSection(Model model){
        List<Movie>movies=movieService.getAllMovies();
        model.addAttribute("movies",movies);
        return "Movie";
    }
    @GetMapping("/cinema/{id}")
    public String shows(@PathVariable Long id, Model model){
        Movie movie=movieService.getMovieById(id);
        List<ShowTime> shows=showTimeService.getShowsByMovie(movie);
        model.addAttribute("movieName",movie.getName());
        model.addAttribute("shows",shows);
        return "Cinema";
    }

    @GetMapping("/cinemaSeating")
    public String seatPage(@RequestParam  String movieName,@RequestParam String showTime,@RequestParam String showDate,@RequestParam String theatre, Model model){
        ShowTime currentShow = showTimeService.getShow(movieName,showDate,showTime);
        Seating seating = seatingService.getSeatingByShow(currentShow);
        List<String> seatRows = Arrays.asList("A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N");
        List<String> bookedSeats = new ArrayList<>();
        List<MovieTicket> bookedTickets = ticketService.getAllTicketsByShowAndSeat(currentShow, seating);
        List<String> availableSeats = new ArrayList<>();

        for(MovieTicket ticket: bookedTickets){
            String[] seatsArr = ticket.getSeats().split(",");
            for (String seat : seatsArr) {
                bookedSeats.add(seat.trim()); // Trim to remove spaces
            }
        }

        for(String row: seatRows) {
            for (int seatNum = 1; seatNum <= 15; seatNum++) {
                String seatLable = row + seatNum;
                if (!bookedSeats.contains(seatLable)) {
                    availableSeats.add(seatLable);
                }
            }
        }

        model.addAttribute("availableSeats",availableSeats);
        model.addAttribute("movieName",movieName);
        model.addAttribute("time",showTime);
        model.addAttribute("date",showDate);
        model.addAttribute("theatre",theatre);
        model.addAttribute("seatRows", seatRows);
        return "movie-seats"; //modify-->3 seating arrangement page la ecntre la gap varla indha code la aprm row ila like a,b,c
    }


    @GetMapping("/order")
    public String orderPage(@RequestParam String movieName, @RequestParam String date, @RequestParam String time, @RequestParam String seats, @RequestParam String row , HttpSession session){
        MovieTicket currentTicket=new MovieTicket();
        ShowTime currentShow = showTimeService.getShow(movieName,date,time);
        Seating seating = seatingService.getSeatingByShow(currentShow);
        currentTicket.setSeating(seating);
        currentTicket.setShowTime(currentShow);
        int seatCount=(seats.length()==1)?1:seats.split(",").length;

        double amount=190*seatCount;

        seating.setAvailableSeats(seating.getAvailableSeats() - seatCount);
        seatingService.update(seating);


        currentTicket.setAmount(amount);
        //currentTicket.setUser(userController.getCurrentuser());
        currentTicket.setUser((User) session.getAttribute("user"));
        currentTicket.setSeats(seats);
        currentTicket.setSeatRow(row);
        ticketService.create(currentTicket);
        //Order order=orderService.createMovieOrder(currentTicket, userController.getCurrentuser(), movieName);
        Order order=orderService.createMovieOrder(currentTicket, (User)session.getAttribute("user"), movieName);

        PaymentResponse paymentResponse=null;
        try {
            paymentResponse=paymentService.createPaymentLink(order);

        }catch (Exception e){
            System.out.println("error while creating payment link\n"+e.getLocalizedMessage()+"\n"+e.getMessage());
        }

        if (paymentResponse!=null&&paymentResponse.getPayment_url()!=null) {
            return "redirect:" + paymentResponse.getPayment_url();
        }
        return "movie-seats";
        // this redirects the payment page which is given by stripe da, after completeing the payment , stripe api wil return sucess or failure ,
    }


}
