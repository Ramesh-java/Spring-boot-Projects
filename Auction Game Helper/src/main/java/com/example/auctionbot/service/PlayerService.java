package com.example.auctionbot.service;

import com.example.auctionbot.model.Player;
import com.example.auctionbot.model.Team;
import com.example.auctionbot.repository.PlayerRepo;
import com.example.auctionbot.repository.TeamRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlayerService {
    private final TeamRepo teamRepo;
    private final PlayerRepo repo;
    public PlayerService(PlayerRepo repo,TeamRepo teamRepo){
        this.repo=repo;
        this.teamRepo=teamRepo;
    }

    public Player sold(String name,int price, Team team){
        Player player=new Player();
        player.setName(name);
        player.setTeam(team);
        player.setPrice(price);



        team.setBalance(team.getBalance()-price);
        teamRepo.save(team);
        System.out.println(team.getBalance());
        return repo.save(player);
    }


    public String rollback(Player lastPlayer) {

         repo.deleteById(lastPlayer.getName());
         //Player player=repo.findById(lastPlayer.getName()).orElse(null);
         Team team=lastPlayer.getTeam();
         team.setBalance(team.getBalance()+lastPlayer.getPrice());
         teamRepo.save(team);

         return lastPlayer.getName();

    }

    public List<Player> getPlayersById(String team) {
        Team team1=teamRepo.findById(team).orElse(null);
        return repo.findAllByTeam(team1);
    }


}
