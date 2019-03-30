package api;

import Server.Game.Player;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {

    PlayerInterface enterGame(String userName , int numPlayers, ClientInterface clientInterface) throws RemoteException;
    PlayerInterface login(String username, int numPlayers, ClientInterface clientInterface) throws RemoteException;

}
