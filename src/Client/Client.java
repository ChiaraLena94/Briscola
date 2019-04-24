package Client;

import Core.Card;
import Core.Deck;
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

    protected Client(String username) throws RemoteException {
        this.username = username;
        try {
            registry = LocateRegistry.getRegistry(1099);
            server = (ServerInterface) registry.lookup(SERVER);
        } catch (RemoteException | NotBoundException e) {
            e.printStackTrace();
        }
    }

    public List<Card> getHand() {
        return hand;
    }

    public PlayerInterface getPlayerInterface() {
        return playerInterface;
    }

    public void enterGame(int numPlayers) throws RemoteException {
        playerInterface = server.enterGame(username, numPlayers, this);
    }

    /**
     * esegue il login ritornando vero se a buon fine, falso altrimenti
     */
    public void login(String username, int numPlayers){
        try {
            playerInterface = server.login(username, numPlayers, this);
            logged = true;
            System.out.println("Ottengo Player Briscola");
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public void setBufferStage(Stage bufferStage){
        this.bufferStage=bufferStage;
    }

    public void createGameStage() {
        gameStage=new Stage();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Gui/fxml/game.fxml"));
            Scene scene= new Scene(root);
            gameStage.setScene(scene);
            gameStage.centerOnScreen();
            gameStage.setTitle(this.username);
            gameStage.show();
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
                    createGameStage();
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

    //Viene chiamato dalla grafica client, selezionando una carta
    public void playCard(Card card) throws  RemoteException{
        hand.remove(card);
        playerInterface.turnCard(card);
    }


    public static Client getInstance(){
        return instance;
    }

    public static Client createInstance(String username) throws RemoteException {
        instance = new Client(username);
        return instance;
    }
}
