package Client.Gui.controllers;

import Client.Client;
import Client.Gui.CardPathLoader;
import Core.Card;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    List<Card> hand=new ArrayList<>();
    CardPathLoader cardPathLoader = new CardPathLoader();
    String temp;


    @FXML
    public ImageView myLeft;

    @FXML
    public ImageView myCenter;

    @FXML
    public ImageView myRight;

    @FXML
    public ImageView briscola;



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hand= Client.getInstance().getHand();
        myLeft.setImage(new Image(getClass().getResourceAsStream("../Resources/"+cardPathLoader.getPath(hand.get(0).getId()))));
        myCenter.setImage(new Image(getClass().getResourceAsStream("../Resources/"+cardPathLoader.getPath(hand.get(1).getId()))));
        myRight.setImage(new Image(getClass().getResourceAsStream("../Resources/"+cardPathLoader.getPath(hand.get(2).getId()))));
        try {
            briscola.setImage(new Image(getClass().getResourceAsStream("../Resources/"+cardPathLoader.getPath(Client.getInstance().getPlayerInterface().getBriscolaCard().getId()))));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
