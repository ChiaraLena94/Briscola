package Client;

import Core.Card;
import Core.Deck;
import api.ClientInterface;
import api.PlayerInterface;
import api.ServerInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class Client extends UnicastRemoteObject implements ClientInterface {
    private  static final String SERVER = "serverBriscola";
    private String username;
    private Registry registry;
    private ServerInterface server;
    private PlayerInterface playerInterface;
    private Deck deck;
    private List<Card> hand;

    protected Client(String username) throws RemoteException {
        this.username = username;
        try {
            registry = LocateRegistry.getRegistry(1099);
            server = (ServerInterface) registry.lookup(SERVER);
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }

    public void enterGame(int numPlayers) throws RemoteException {
        playerInterface = server.enterGame(username, numPlayers, this);
    }


    @Override
    public void receiveHand(List<Card> hand) throws RemoteException {
        this.hand=hand;
        // put this deck in the graphics e far pescare le carte ai giocatori a
    }

    @Override
    public void selectCard() throws RemoteException {

        //chiami un metodo su main gui player per dirgli è il tuo turno.

    }


    @Override
    public void drawCard() throws RemoteException {
       hand.add(playerInterface.drawCard());
       //chiama un metodo nella gui che fa l' animazione del pescaggio
    }

    @Override
    public void notifyEndGame(String winner) throws RemoteException {
        //a grafica ti appare se hai vinto o meno
    }

    //Viene chiamato dalla grafica client, selezionando una carta
    public void playCard(Card card) throws  RemoteException{
        hand.remove(card);
        playerInterface.turnCard(card);
    }


}
