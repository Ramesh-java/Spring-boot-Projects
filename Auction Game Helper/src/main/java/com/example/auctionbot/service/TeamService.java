package com.example.auctionbot.service;

import com.example.auctionbot.model.Team;
import com.example.auctionbot.repository.TeamRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeamService {

    private final TeamRepo repo;

    public TeamService(TeamRepo team){
        repo=team;

    }

    public Team getTeamByName(String name) {
        Team team= repo.findById(name).orElse(null);
        return team;
        //return (Team) repo.findById(name).orElse(null);
    }

    public boolean isValid(String name,int price){
        Team team= repo.findById(name).orElse(null);
        if (price==0|| price<0||team==null){
            return false;
        }
        int balance=team.getBalance();
       return (balance-price)>=0;
    }

    public int getBalance(String name){
        Team team= repo.findById(name).orElse(null);
        if (team==null){
            return -1;
        }
        return team.getBalance();
    }


    public List<Team> getAllTeams() {
        return repo.findAll();
    }
}
