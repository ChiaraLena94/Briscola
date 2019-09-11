package Server.Game;

import Core.Card;
import api.ClientInterface;
import api.PlayerInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player extends UnicastRemoteObject implements PlayerInterface {
    private int points;
    private String team;
    private String username;
    private ClientInterface clientPlayer;
    private Game game;

    public Player(String username, ClientInterface clientInterface, Game game) throws RemoteException {
        this.username = username;
        this.clientPlayer = clientInterface;
        this.points = 0;
        this.game = game;
        this.team=" ";
    }

    //getter methods
    public void setTeam(String team) {
        this.team = team;
    }

    public ClientInterface getClientPlayer() {
        return clientPlayer;
    }

    public String getUsername() {
        return username;
    }

    public int getPoints() {
        return points;
    }



    public void addPoints(int points) {
        this.points += points;
    }

    public void sendHand(List<Card> hand) throws RemoteException {
        clientPlayer.receiveHand(hand);
    }

    public void yourTurn() throws RemoteException {
        clientPlayer.selectCard();
    }

    public void updateWithPlayedCard(String username, int idTurnCard) throws RemoteException {
        clientPlayer.updateWithPlayedCard(username,idTurnCard);
    }

    public void updateTurnWinner(String turnWinnerPlayer) throws RemoteException {
        clientPlayer.updateTurnWinner(turnWinnerPlayer);
    }


    @Override
    public void turnCard(Card card) throws RemoteException {
        game.addCardToTurn(card,this);
    }

    @Override
    public Card drawCard() throws RemoteException {
       return game.getDeck().drawCard() ;
    }

    @Override
    public Card getBriscolaCard() throws RemoteException {
        return game.getDeck().getBriscolaCard();
    }

    @Override
    public int getNumPlayersInGame() throws RemoteException {
        return game.getNumPlayers();
    }

    @Override
    public List<String> getPlayerList() throws RemoteException {
        List<String> playerUsername = new ArrayList<String>();
        game.getPlayers().forEach((k,v) -> playerUsername.add(v.getUsername()));
        return playerUsername;
    }

    @Override
    public Map<String, Integer> getMapPoints (){
        HashMap<String, Integer> mapPoints= new HashMap<>();
        game.getPlayers().forEach((k,v) -> mapPoints.put(v.getUsername(), v.getPoints()));
        return mapPoints;
    }

    @Override
    public String getTeam() {
        return team;
    }

    @Override
    public Map<String,String> getplayersTeam(){
        return game.getPlayersTeam();
    }

}
