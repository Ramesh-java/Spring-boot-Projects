package com.example.auctionbot;

import com.example.auctionbot.model.Player;
import com.example.auctionbot.model.Team;
import com.example.auctionbot.service.PlayerService;
import com.example.auctionbot.service.TeamService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/auction")
@CrossOrigin("*")
public class BotController {

    /*@GetMapping("allCommands")
    public Message showAllCommands(){
        StringBuilder commands=new StringBuilder();
        int index=1;
        commands.append(index++).append(" ").append("<!sell team_name> ").append("Usage: selling players")
                .append("\n")
                .append(index++).append(" ").append("<!balance team_name> ").append("Usage: view balance")
                .append("\n")
                .append(index++).append(" ").append("<!allBalance").append("Usage: to view everyone's balance");

    }*/

    private final TeamService teamService;
    private final PlayerService playerService;

    Player lastPlayer=null;

    public BotController(TeamService teamService, PlayerService playerService){
        this.teamService=teamService;
        this.playerService=playerService;
    }


    @PostMapping("/bid")
    public Message sold(@RequestBody Data data){
        String name=data.getName();
        int price=data.getPrice();
        String team=data.getTeam();
        Message message=new Message();
        StringBuilder response=new StringBuilder();
        if (teamService.isValid(team,price)){

            Player player=playerService.sold(name,price,teamService.getTeamByName(team));
            lastPlayer=player;
            System.out.println("The player "+name+" is sold to "+team+" for "+price+"M");
            response.append(name).append(" is sold to ").append(team).append(" for ").append(price).append("M");
            message.setContent(response.toString());
            return message;
        }
        response.append("Insufficient Balance for ").append(team).append(" so cant able to register ").append(name);
        message.setContent(response.toString());
        return message;
    }

    @GetMapping("/balance")
    public Message getBalance(@RequestParam("team") String team){
        //String team=name.getTeam();
        System.out.println(team);
        int balance=teamService.getBalance(team);
        StringBuilder response=new StringBuilder();
        Message message=new Message();
        if (balance!=-1){

            response.append(team).append(" Current Balance ").append(balance);

            message.setContent(response.toString());
            return (message);
        }
        response.append(team).append(" Not found , check the spelling");
        message.setContent(response.toString());
        return (message);
    }

    @GetMapping("getAllBalance")
    public Message getAllBalance(){
        StringBuilder response=new StringBuilder();
        Message message=new Message();
        List<Team>teams=teamService.getAllTeams();
        for (Team team:teams){
            response.append(team.getName()).append(" ").append(team.getBalance()).append("M Balance").append("\n\n");
        }

        message.setContent(response.toString());

        return message;
    }

    @GetMapping("rollback")
    public Message rollBack(){
        StringBuilder response=new StringBuilder();
        Message message=new Message();
        if (lastPlayer!=null){
            response.append(playerService.rollback(lastPlayer));
        }

        if (lastPlayer==null){
            response.append("player already rolled back");
            message.setContent(response.toString());
        }else {
            response.append(" rolled back");
            message.setContent(response.toString());
            lastPlayer=null;
        }
        return message;
    }

    @GetMapping("getPlayers/{team}")
    public String getAll(@PathVariable String team){
        StringBuilder response=new StringBuilder();
        List<Player>list=playerService.getPlayersById(team);
        int index=1;
        for (Player player:list){
            response.append(index++).append(" ").append(player.getName()).append(" ").append(player.getPrice()).append("\n");
        }
        Message message=new Message();
        message.setContent(response.toString());
        return message.getContent();
    }

}