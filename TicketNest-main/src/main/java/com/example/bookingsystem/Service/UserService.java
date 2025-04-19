package com.example.bookingsystem.Service;

import com.example.bookingsystem.Model.User;
import com.example.bookingsystem.Repository.UserRepository;
import com.stripe.model.tax.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JavaMailSender mailSender;
    private  final BCryptPasswordEncoder bCryptPasswordEncoder;
    public UserService (BCryptPasswordEncoder bCryptPasswordEncoder){
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
    }
    public User create(String name,String email,String password){
        User user=new User();
        user.setName(name);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setEmail(email);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(user.getEmail());
        message.setSubject("Welcome to TicketNest!!!");
        message.setText(
                "🎉 Hello " + user.getName() + "!\n\n" +
                        "Welcome to **TicketNest**! 🎟️✨\n" +
                        "We're super excited to have you on board. 😊🙌\n\n" +
                        "With TicketNest, you can easily book your 🎫 *Train*, 🚌 *Bus*, and 🎬 *Movie* tickets — all in one place!\n\n" +
                        "🚀 Start exploring now and enjoy smooth, hassle-free bookings.\n\n" +
                        "If you need any help, our support team is just a click away. 💬\n\n" +
                        "Happy Booking! 💖\n" +
                        "- The TicketNest Team 💜🤍"
        );

        mailSender.send(message);
        return userRepository.save(user);
    }

    public User findByEmail(String email,String password){
        User user= userRepository.findUserByEmail(email);
        if (user!=null &&passwordValidate(user,password)) {
            return user;
        }else {
            return null;
        }
    }

    public boolean passwordValidate(User user,String raw){
        return  (bCryptPasswordEncoder.matches(raw,user.getPassword()));
    }

    public User returnUserById(Long id){
        return userRepository.findById(id).orElse(null);
    }


}
