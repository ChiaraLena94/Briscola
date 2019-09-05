package Client;

import Client.Gui.GameGui;
import Client.Gui.LoginGui;
import Client.Gui.MainGui;
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
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Client extends UnicastRemoteObject implements ClientInterface {
    private  static final String SERVER = "serverBriscola";
    private String username;
    private Registry registry;
    private ServerInterface server;
    private PlayerInterface playerInterface;
    private Deck deck;
    private Map<Integer, Card> hand= new HashMap<>();
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

    public Map<Integer, Card> getHand() {
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
    public void receiveHand(List<Card> handList) throws RemoteException {
        int index=0;
        for(Card card:handList) {
                hand.put(index, card);
                index++;
        }
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
        Platform.runLater(() ->{
            gameGui.getControlLabel().setText("è il tuo turno!");
            gameGui.addListenerToCards();
        });
    }

    @Override
    public void drawCard() throws RemoteException {
        int i=0;
        Card c = playerInterface.drawCard();
        i=getEmptyIndex();
        hand.put(i, c);
        gameGui.updateHand(c);
        if (!(Integer.parseInt(gameGui.getDeckLabel().getText()) == 2))
            updateDeckLabel();
        else
            deleteDeck();
    }

    private void deleteDeck() {
        Platform.runLater(() ->{
            gameGui.getDeckLabel().setText(" ");
            gameGui.getDeck().setImage(null);});
        gameGui.getBriscola().setImage(null);

    }

    private int getEmptyIndex() {
        List<Integer> list= new ArrayList<>();
        for (Map.Entry entry : hand.entrySet()) {
            list.add((Integer) entry.getKey());

        }
        System.out.println("MAPPA:"+hand.values());
        if (list.get(0)+list.get(1)==1) {
            return 2;
        }
        if (list.get(0)+list.get(1)==2) {
            return 1;
        }
        if (list.get(0)+list.get(1)==3) {
            return 0;
        }
        System.out.println("c'è stato un errore nel calcolo!!!!!");
        return 42;
    }

    @Override
    public void notifyEndGame(String winner) throws RemoteException {
        System.out.println("\n\n\n sono in notifyendgame. Sono il player "+username);
        System.out.println("la partita è finita e il vincitore è "+winner);
        Platform.runLater(() ->{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("End Game");
            alert.setHeaderText("This game is finished!");
            alert.setContentText("The winner is: "+winner);
            alert.showAndWait();});
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Platform.runLater(() ->{
            gameGui.getStage().close();
            try {
                LoginGui.createLoginGui();

            } catch (IOException e) {
                e.printStackTrace();
            }
            MainGui.getPrimaryStage().show();
            });
    }

    @Override
    public void updateWithPlayedCard(String lastPlayer,int idTurnCard) throws RemoteException {
        Platform.runLater(
                () -> {
                    try {
                        gameGui.addCardToBoard(lastPlayer, idTurnCard);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
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

    public void updateDeckLabel() {
        Platform.runLater(() ->{
            try {
                gameGui.getDeckLabel().setText(String.valueOf(Integer.parseInt(gameGui.getDeckLabel().getText()) -getPlayerList().size()));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    //Viene chiamato dalla grafica client, selezionando una carta
    public void playCard(Card card, int pos) throws  RemoteException{
        hand.remove(pos, card);
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
