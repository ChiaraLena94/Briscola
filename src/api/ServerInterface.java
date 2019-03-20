package api;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ServerInterface extends Remote {

    PlayerInterface enterGame(String userName , int numPlayers, ClientInterface clientInterface) throws RemoteException;

}
