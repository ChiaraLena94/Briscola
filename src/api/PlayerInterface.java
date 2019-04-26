package api;

import Core.Card;
import Core.Seed;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface PlayerInterface extends Remote {

    public void turnCard(Card card) throws RemoteException;

    public Card drawCard() throws RemoteException;

    public Card getBriscolaCard() throws RemoteException;

    public int getNumPlayersInGame() throws RemoteException;
}
