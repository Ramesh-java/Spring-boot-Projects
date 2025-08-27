package com.example.themleaf.service;

import com.example.themleaf.model.Task;
import com.example.themleaf.model.User;
import com.example.themleaf.repository.USERREPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class Uservice {
    public Uservice(USERREPO userrepo,BCryptPasswordEncoder bCryptPasswordEncoder){
        this.bCryptPasswordEncoder=bCryptPasswordEncoder;
        repo=userrepo;
    }

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private  final USERREPO repo;

    public  List<User> getAll() {
        return repo.findAll();
    }

    public User create(String username,String password ,String number){
        User user=new User();
        user.setName(username);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setNumber(Long.parseLong(number));
        return repo.save(user);
    }

    public Optional<User> getUser(Long number){
        return repo.getByNumber(number);
    }

    public User ValidateUser(String number,String password){
        Optional<User>users=getUser(Long.parseLong(number));
        if (users.isPresent()){
            User user=users.get();
            if(bCryptPasswordEncoder.matches(password,user.getPassword())){
                return user;
            }
        }
        return null;
    }
    /*public User getUser(String number,String password ){
        Optional<User>optionalUser=repo.getByNumber(number);
    }*/
}
