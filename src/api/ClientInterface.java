package api;

import Core.Card;
import Server.Game.Player;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

public interface ClientInterface extends Remote {

    public void receiveHand(List<Card> hand) throws RemoteException;

    public void selectCard() throws RemoteException;

    public void drawCard() throws RemoteException;

    public void notifyEndGame(String winner) throws RemoteException;

    public void updateWithPlayedCard(int idTurnCard) throws RemoteException;

    public void updateTurnWinner(String turnWinnerPlayer) throws RemoteException;

}
