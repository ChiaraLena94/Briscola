package Client.Gui.controllers;

import Client.Client;
import Client.Gui.CardPathLoader;
import Client.Gui.animations.ScaleAnimation;
import Core.Card;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    List<Card> hand=new ArrayList<>();
    CardPathLoader cardPathLoader = new CardPathLoader();


    @FXML
    public ImageView cardPlayer1;

    @FXML
    public ImageView cardPlayer2;

    @FXML
    public ImageView cardPlayer3;

    @FXML
    public ImageView cardPlayer4;

    @FXML
    public ImageView myLeft;

    @FXML
    public ImageView myCenter;

    @FXML
    public ImageView myRight;

    @FXML
    public ImageView briscola;

    @FXML
    public ImageView adv1Right;

    @FXML
    public ImageView adv1Center;

    @FXML
    public ImageView adv1Left;

    @FXML
    public ImageView adv3Right;

    @FXML
    public ImageView adv3Center;

    @FXML
    public ImageView adv3Left;

    @FXML
    public ImageView adv4Right;

    @FXML
    public ImageView adv4Center;

    @FXML
    public ImageView adv4Left;

    @FXML
    public ImageView deck;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hand= Client.getInstance().getHand();
        myLeft.setImage(new Image(getClass().getResourceAsStream("../Resources/"+cardPathLoader.getPath(hand.get(0).getId()))));
        myCenter.setImage(new Image(getClass().getResourceAsStream("../Resources/"+cardPathLoader.getPath(hand.get(1).getId()))));
        myRight.setImage(new Image(getClass().getResourceAsStream("../Resources/"+cardPathLoader.getPath(hand.get(2).getId()))));
        adv1Left.setImage(new Image(getClass().getResourceAsStream("../Resources/retroCarta.png")));
        adv1Center.setImage(new Image(getClass().getResourceAsStream("../Resources/retroCarta.png")));
        adv1Right.setImage(new Image(getClass().getResourceAsStream("../Resources/retroCarta.png")));
        deck.setImage(new Image(getClass().getResourceAsStream("../Resources/retroCarta.png")));

        try {
            if (Client.getInstance().getPlayerInterface().getNumPlayersInGame()>2) {
                adv3Left.setImage(new Image(getClass().getResourceAsStream("../Resources/retroCarta.png")));
                adv3Center.setImage(new Image(getClass().getResourceAsStream("../Resources/retroCarta.png")));
                adv3Right.setImage(new Image(getClass().getResourceAsStream("../Resources/retroCarta.png")));
                if (Client.getInstance().getPlayerInterface().getNumPlayersInGame()==4){
                    adv4Left.setImage(new Image(getClass().getResourceAsStream("../Resources/retroCarta.png")));
                    adv4Center.setImage(new Image(getClass().getResourceAsStream("../Resources/retroCarta.png")));
                    adv4Right.setImage(new Image(getClass().getResourceAsStream("../Resources/retroCarta.png")));
                }
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        try {
            briscola.setImage(new Image(getClass().getResourceAsStream("../Resources/"+cardPathLoader.getPath(Client.getInstance().getPlayerInterface().getBriscolaCard().getId()))));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }



    public void zoomImageLeft(MouseEvent mouseEvent){
        myLeft.setOnMouseEntered(e -> {
            new ScaleAnimation(myLeft,1.75,1.75, Duration.millis(300)).playAnimation();
            myLeft.setRotate(0);
            myLeft.toFront();
        });
        myLeft.setOnMouseExited(e -> {
            new ScaleAnimation(myLeft,1,1, Duration.millis(300)).playAnimation();
            myLeft.setRotate(340);
            myLeft.toBack();
        });
    }

    public void zoomImageRight(MouseEvent mouseEvent){
        myRight.setOnMouseEntered(e -> {
            new ScaleAnimation(myRight,1.75,1.75, Duration.millis(300)).playAnimation();
            myRight.setRotate(0);
        });
        myRight.setOnMouseExited(e -> {
            new ScaleAnimation(myRight,1,1, Duration.millis(300)).playAnimation();
            myRight.setRotate(20);
        });
    }

    public void zoomImageCenter(MouseEvent mouseEvent){
        myCenter.setOnMouseEntered(e -> {
            new ScaleAnimation(myCenter,1.75,1.75, Duration.millis(300)).playAnimation();
            myCenter.toFront();
        });
        myCenter.setOnMouseExited(e -> {
            new ScaleAnimation(myCenter,1,1, Duration.millis(300)).playAnimation();
            myCenter.toBack();
        });
    }

    public void zoomImageBriscola(MouseEvent mouseEvent){
        briscola.setOnMouseEntered(e -> {
            new ScaleAnimation(briscola,1.75,1.75, Duration.millis(300)).playAnimation();
            briscola.setRotate(0);
            briscola.toFront();
        });
        briscola.setOnMouseExited(e -> {
            new ScaleAnimation(briscola,1,1, Duration.millis(300)).playAnimation();
            briscola.setRotate(90);
            briscola.toBack();
        });
    }

    public void insertCard (String image) {
        if (cardPlayer1.getImage()==null) {
            cardPlayer1.setImage(new Image(getClass().getResourceAsStream("../Resources/"+image)));
        }
        else if (cardPlayer2.getImage()==null) {
            cardPlayer2.setImage(new Image(getClass().getResourceAsStream("../Resources/"+image)));
        }
        else if (cardPlayer3.getImage()==null) {
            cardPlayer3.setImage(new Image(getClass().getResourceAsStream("../Resources/"+image)));
        }
        else{
            cardPlayer4.setImage(new Image(getClass().getResourceAsStream("../Resources/"+image)));
        }
    }

    public void chooseCardLeft (MouseEvent mouseEvent) throws RemoteException {
        System.out.println("sono in chooseCardLeft, adeso chiamo l'interfaccia player nel client");
        insertCard(cardPathLoader.getPath(Client.getInstance().getHand().get(0).getId()));
        Client.getInstance().playCard(Client.getInstance().getHand().get(0));
        //Client.getInstance().getPlayerInterface().turnCard(hand.get(0));
        myLeft.setImage(null);
        System.out.println("sono in game conttroller e ho spostato la carta");
    }

    public void chooseCardCenter (MouseEvent mouseEvent) throws RemoteException {
        Client.getInstance().getPlayerInterface().turnCard(hand.get(1));
        myCenter.setImage(null);
        insertCard(cardPathLoader.getPath(hand.get(1).getId()));
    }

    public void chooseCardRight (MouseEvent mouseEvent) throws RemoteException {
        Client.getInstance().getPlayerInterface().turnCard(hand.get(2));
        myRight.setImage(null);
        insertCard(cardPathLoader.getPath(hand.get(2).getId()));
    }

}
