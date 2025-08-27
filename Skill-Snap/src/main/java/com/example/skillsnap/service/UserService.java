package com.example.skillsnap.service;

import com.example.skillsnap.model.Customer;
import com.example.skillsnap.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private UserRepository userRepository;
    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    @Transactional
    public void create
            (Customer customer){
        Customer customer1=new Customer();
        customer1.setUsername(customer.getUsername());
        customer1.setEmail(customer.getEmail());
        customer1.setPassword(customer.getPassword());

        userRepository.save(customer);
    }

    public boolean validateUser(String email,String password){

        Optional<Customer> customer1=userRepository.findCustomerByEmail(email);
        if(customer1.isPresent()){
            return customer1.get().getPassword().equals(password);
        }
        return false;
    }

    public Customer getCustomer(int id) {
        return userRepository.findById(Long.parseLong(String.valueOf(id))).orElse(null);

    }

    public Customer get(int id1){
        Long id=Long.parseLong(String.valueOf(id1));

        return userRepository.findById(id).orElse(null);


    }
}