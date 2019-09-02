package Server;

import Server.Game.*;
import api.ClientInterface;
import api.PlayerInterface;
import api.ServerInterface;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Server extends UnicastRemoteObject implements ServerInterface {
    private Map<Integer, List<Game>> gamesMap;

    public Server() throws RemoteException {
        initializeGameMap();
    }

    private void initializeGameMap() {
        gamesMap = new HashMap<>();
        gamesMap.put(2, new ArrayList<>());
        gamesMap.put(3, new ArrayList<>());
        gamesMap.put(4, new ArrayList<>());
        gamesMap.put(5, new ArrayList<>());
    }

    @Override
    public synchronized PlayerInterface enterGame(String username, int numPlayers, ClientInterface clientInterface) throws RemoteException {
        if(getFreeGame(numPlayers) == null){
            addNewGame(numPlayers);
        }
        Game g = getFreeGame(numPlayers);
        Player p = new Player(username, clientInterface,g);
        g.addPlayer(p);
        System.out.println("Name : " + username + "has entered the game");
        return p;
    }

    private Game getFreeGame(int numPlayers) {
        for(Game g : gamesMap.get(numPlayers)) {
            System.out.println("sto gioco se chiama: "+g);
            System.out.println("stampo valore di g.getFull"+ g.getFull());
           if (!g.getFull())
               return g;
        }
        return null;
    }

    private void addNewGame(int numPlayers) {
        switch (numPlayers){
            case 2:
                gamesMap.get(numPlayers).add(new TwoPlayersGame(numPlayers));
            break;
            case 3:
                gamesMap.get(numPlayers).add(new ThreeplayersGame(numPlayers));
            break;
            case 4:
                gamesMap.get(numPlayers).add(new FourPlayersGame(numPlayers));
            break;
            default:
                System.out.println("ERROR");
            break;

        }
    }

    @Override
    public synchronized PlayerInterface login(String username, int numPlayers, ClientInterface clientInterface) throws RemoteException {
        return enterGame(username, numPlayers, clientInterface);
    }

}
