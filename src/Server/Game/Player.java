package Server.Game;

import Core.Card;
import Core.Deck;
import api.ClientInterface;
import api.PlayerInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class Player extends UnicastRemoteObject implements PlayerInterface {
    private int points;

    private String username;

    private ClientInterface clientPlayer;
    private Game game;
    public Player(String username, ClientInterface clientInterface, Game game) throws RemoteException {
        this.username = username;
        this.clientPlayer = clientInterface;
        this.points = 0;
        this.game = game;
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
       game.addCardToTurn(card,this);
    }

    @Override
    public Card drawCard() throws RemoteException {
       return game.getDeck().drawCard() ;
    }
}
