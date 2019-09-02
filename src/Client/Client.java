package Client;

import Client.Gui.GameGui;
import Core.Card;
import Core.Deck;
import Server.Game.Player;
import api.ClientInterface;
import api.PlayerInterface;
import api.ServerInterface;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;

public class Client extends UnicastRemoteObject implements ClientInterface {
    private  static final String SERVER = "serverBriscola";
    private String username;
    private Registry registry;
    private ServerInterface server;
    private PlayerInterface playerInterface;
    private Deck deck;
    private List<Card> hand;
    private boolean logged=false;
    private Stage bufferStage=null;
    private Stage gameStage=null;
    private static Client instance;
    private GameGui gameGui;

    protected Client(String username) throws RemoteException {
        this.username = username;
        try {
            registry = LocateRegistry.getRegistry(1099);
            server = (ServerInterface) registry.lookup(SERVER);
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }

    public GameGui getGameGui() {
        return gameGui;
    }

    public String getUsername() {
        return username;
    }

    public List<Card> getHand() {
        return hand;
    }

    //getter and setter methods
    public PlayerInterface getPlayerInterface() {
        return playerInterface;
    }

    public void setBufferStage(Stage bufferStage){
        this.bufferStage=bufferStage;
    }


    public void enterGame(int numPlayers) throws RemoteException {
        playerInterface = server.enterGame(username, numPlayers, this);
    }

    public void login(String username, int numPlayers){
        try {
            playerInterface = server.login(username, numPlayers, this);
            logged = true;
            System.out.println("Ottengo Player Briscola");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void createGameStage() throws IOException {
        try {
            gameGui = new GameGui();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void receiveHand(List<Card> hand) throws RemoteException {
        this.hand=hand;
        Platform.runLater(
                () -> {
                    bufferStage.close();
                    try {
                        createGameStage();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
        );
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

    @Override
    public void updateWithPlayedCard(int idTurnCard) throws RemoteException {
        Platform.runLater(
                () -> {
                    gameGui.addCardToBoard(idTurnCard);
                }
        );

    }

    @Override
    public void updateTurnWinner(String turnWinnerPlayer) throws RemoteException {
        Platform.runLater(
                () -> {
                    try {
                        gameGui.endTurn(turnWinnerPlayer);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }
        );
    }


    //Viene chiamato dalla grafica client, selezionando una carta
    public void playCard(Card card) throws  RemoteException{
        hand.remove(card);
        System.out.println("sono in client-playcard: la carta è "+card.getId()+card.getNum()+card.getSeed());
        playerInterface.turnCard(card);
    }

    //Instance methods. they create the instance client
    public static Client getInstance(){
        return instance;
    }

    public static Client createInstance(String username) throws RemoteException {
        instance = new Client(username);
        return instance;
    }

    public List<String> getPlayerList() throws RemoteException {
        return playerInterface.getPlayerList();
    }

    public Map<String, Integer> getMapPoints() throws RemoteException {
        return playerInterface.getMapPoints();
    }
}
