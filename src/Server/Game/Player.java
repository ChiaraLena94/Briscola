package Server.Game;

import Core.Card;
import Core.Seed;
import api.ClientInterface;
import api.PlayerInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

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
        this.team="noTeam";
    }

    public String getTeam() {
        return team;
    }

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

    @Override
    public void turnCard(Card card) throws RemoteException {
        System.out.println("sono nel player" +username+", adesso aggiungo la carta a turnCards");
       game.addCardToTurn(card,this);
    }

    @Override
    public Card drawCard() throws RemoteException {
       return game.getDeck().drawCard() ;
    }

    @Override
    public Card getBriscolaCard() throws RemoteException {
        return game.getDeck().getBriscola();
    }

    @Override
    public int getNumPlayersInGame() throws RemoteException {
        return game.getNumPlayers();
    }
}
