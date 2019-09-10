package api;

import Core.Card;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public interface ClientInterface extends Remote {

    public void receiveHand(List<Card> hand) throws RemoteException;

    public void selectCard() throws RemoteException;

    public void drawCard() throws RemoteException;

    public void notifyEndGame(String winner, Map<String, Integer> finalMap) throws RemoteException;

    public void updateWithPlayedCard(String lastPlayer, int idTurnCard) throws RemoteException;

    public void updateTurnWinner(String turnWinnerPlayer) throws RemoteException;

}
