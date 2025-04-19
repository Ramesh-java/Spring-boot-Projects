package com.example.bookingsystem.Service;


import com.example.bookingsystem.Model.Bus.BusTicket;
import com.example.bookingsystem.Model.Movie.Movie;
import com.example.bookingsystem.Model.Order;
import com.example.bookingsystem.Model.Movie.MovieTicket;
import com.example.bookingsystem.Model.Train.TrainTicket;
import com.example.bookingsystem.Model.User;
import com.example.bookingsystem.Repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    SimpleMailMessage message = new SimpleMailMessage();
    @Autowired
    private JavaMailSenderImpl mailSender;

    public Order createMovieOrder(MovieTicket ticket, User user, String movieName){
        Order order=new Order();
        order.setTicket(ticket);
        order.setTotalAmount(ticket.getAmount());
        order.setBookingType("Movie");
        order.setUserId(user.getId());
        message.setTo(user.getEmail());
        message.setSubject("Booking Confirmed!!!🎉");
        message.setText(
                "🎉 Hello " + user.getName() + ",\n\n" +
                        "Your movie tickets 🎬 for *" + movieName + "* have been booked successfully! ✅\n\n" +
                        "🎭 Theatre: " +ticket.getShowTime().getTheatre() + "\n" +
                        "🗓️ Date: " +ticket.getShowTime().getDate()+ "\n" +
                        "⏰ Time: " +ticket.getShowTime().getTime() + "\n" +
                        "💺 Seats: " + ticket.getSeats() + "\n" +
                        "💰 Total Amount Paid: ₹" + ticket.getAmount() + "\n\n" +
                        "Please arrive at the venue 15 minutes early and carry a digital or printed copy of this confirmation.\n\n" +
                        "Enjoy your movie! 🍿🎥\n\n" +
                        "Thank you for choosing TicketNest! 💜🤍"
        );
        mailSender.send(message);
        return orderRepository.save(order);
    }

    public Order createBusOrder(BusTicket busTicket,User user){
        Order order=new Order();
        order.setBusTicket(busTicket);
        order.setTotalAmount(busTicket.getTotalAmount());
        order.setUserId(user.getId());
        order.setBookingType("Bus");
        message.setTo(user.getEmail());
        message.setSubject("Booking Confirmed!!!🎉");
        message.setText(
                "🚌 Hello " + user.getName() + ",\n\n" +
                        "Your bus ticket has been successfully booked! ✅\n\n" +
                        "🚌 Bus Name: " + busTicket.getBus().getName() + "\n" +
                        "📍 From: " + busTicket.getBus().getSource() +
                        " → To: " + busTicket.getBus().getDestination() + "\n" +
                        "🗓️ Date: " + busTicket.getBus().getDate() + "\n" +
                        "⏰ Departure: " + busTicket.getBus().getDepartureTime() +
                        " | Arrival: " + busTicket.getBus().getArrivalTime() + "\n" +
                        "💺 Seats Booked: " + busTicket.getSeats() + "\n" +
                        "💰 Amount Paid: ₹" + busTicket.getTotalAmount() + "\n\n" +
                        "Please be at the boarding point at least 15 minutes before departure.\n\n" +
                        "Thank you for choosing TicketNest! 💜🤍"
        );
        mailSender.send(message);
        return orderRepository.save(order);
    }

    public Order createTrainOrder(TrainTicket trainTicket, User user){
        Order order=new Order();
        order.setTrainTicket(trainTicket);
        order.setTotalAmount(trainTicket.getCoach().getPrice());
        order.setUserId(user.getId());
        order.setBookingType("Train");
        message.setTo(user.getEmail());
        message.setSubject("Booking Confirmed!!!🎉");
        message.setText(
                "🚆 Hello " + user.getName() + ",\n\n" +
                        "Your train ticket has been booked successfully! ✅\n\n" +
                        "🚋 Train Name: " + trainTicket.getTrain().getName() + "\n" +
                        "📍 From: " + trainTicket.getTrain().getSource() +
                        " → To: " + trainTicket.getTrain().getDestination() + "\n" +
                        "🛏️ Coach: " + trainTicket.getCoach().getName() + "\n" +
                        "🗓️ Date: " + trainTicket.getTrain().getDate() + "\n" +
                        "⏰ Departure Time: " + trainTicket.getTrain().getDepartureTime() +
                        " | Arrival Time: " + trainTicket.getTrain().getArrivalTime() + "\n" +
                        "💰 Amount Paid: ₹" + trainTicket.getCoach().getPrice() + "\n\n" +
                        "Please be at the station at least 15 minutes before departure.\n\n" +
                        "Thank you for booking with TicketNest! 🚉💜"
        );
        mailSender.send(message);
        return orderRepository.save(order);
    }

    public Order getOrder(Long orderId) {
        return orderRepository.findById(orderId).orElse(null);

    }

    public void save(Order order) {
        orderRepository.save(order);
    }
}