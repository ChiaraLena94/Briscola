package Client.Gui.controllers;

import Client.Client;
import Client.Gui.CardPathLoader;
import Client.Gui.GameGui;
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
    GameGui gameGui=null;


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
        addListenerToCards();

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

    private void addListenerToCards(){
        myLeft.setOnMouseClicked(mouseEvent -> {
            try {
                chooseCard(mouseEvent,0);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });

        myCenter.setOnMouseClicked(mouseEvent -> {
            try {
                chooseCard(mouseEvent,1);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });

        myRight.setOnMouseClicked(mouseEvent -> {
            try {
                chooseCard(mouseEvent,2);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }


    public void chooseCard (MouseEvent mouseEvent, int idCard) throws RemoteException {
        gameGui=Client.getInstance().getGameGui();
        System.out.println("sono in chooseCard, adesso chiamo l'interfaccia player nel client. gamegui è "+gameGui);
        gameGui.insertCard(cardPathLoader.getPath(Client.getInstance().getHand().get(idCard).getId()));
        Client.getInstance().playCard(Client.getInstance().getHand().get(idCard));
        deleteCorrectCard(idCard);
        System.out.println("sono in game conttroller e ho spostato la carta");
    }

    private void deleteCorrectCard(int idCard) {
        if (idCard==0) myLeft.setImage(null);
        else if (idCard==1) myCenter.setImage(null);
        else myRight.setImage(null);
    }


}
