package Client.Gui.controllers;

import Client.Client;
import Client.Gui.CardPathLoader;
import Client.Gui.GameGui;
import Client.Gui.animations.ScaleAnimation;
import Core.Card;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.*;

public class GameController implements Initializable {
    Map<Integer,Card> hand=new HashMap<>();
    CardPathLoader cardPathLoader = new CardPathLoader();
    GameGui gameGui=null;
    List<String> playerList= new ArrayList<>();


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

    @FXML
    public Label myName;

    @FXML
    public Label myPoints;

    @FXML
    public Label nameAdv1;

    @FXML
    public Label nameAdv3;

    @FXML
    public Label nameAdv4;

    @FXML
    public Label controlLabel;

    @FXML
    public Label deckLabel;

    @FXML
    public Label myTeam;

    @FXML
    public Label teamAdv1;

    @FXML
    public Label teamAdv3;

    @FXML
    public Label teamAdv4;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hand= Client.getInstance().getHand();
        System.out.println("stampo la mappa: "+hand.values());
        myLeft.setImage(new Image(getClass().getResourceAsStream("../Resources/"+cardPathLoader.getPath(((Card) hand.get(0)).getId()))));
        myCenter.setImage(new Image(getClass().getResourceAsStream("../Resources/"+cardPathLoader.getPath(((Card) hand.get(1)).getId()))));
        myRight.setImage(new Image(getClass().getResourceAsStream("../Resources/"+cardPathLoader.getPath(((Card) hand.get(2)).getId()))));
        deck.setImage(new Image(getClass().getResourceAsStream("../Resources/retroCarta.png")));
        myName.setText(Client.getInstance().getUsername());
        myPoints.setText("0");
        //myTeam.setText("            ");
        try {
            setNumOfCardsInDeck();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        try {
            playerList=Client.getInstance().getPlayerList();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        if (playerList.size()==4) {
            try {
                initialize4AdvBoard(playerList);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        else {
            playerList.remove(Client.getInstance().getUsername());
            try {
                initializeAdv1(playerList.get(0));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            initializeOtherAdv();
        }
        addFirstListener();
        try {
            briscola.setImage(new Image(getClass().getResourceAsStream("../Resources/"+cardPathLoader.getPath(Client.getInstance().getPlayerInterface().getBriscolaCard().getId()))));
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void initialize4AdvBoard(List<String> playerList ) throws RemoteException {
        try {
            myTeam.setText(Client.getInstance().getPlayerInterface().getTeam());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        playerList.add(playerList.get(0));
        playerList.add(playerList.get(1));
        playerList.add(playerList.get(2));
        for(int i=0; i<playerList.size()-3; i++){
            if (Client.getInstance().getUsername().equals(playerList.get(i))) {
                initializeAdv1(playerList.get(i+2));
                initializeAdv3(playerList.get(i+1));
                initializeAdv4(playerList.get(i+3));
            }
        }
    }

    private void setNumOfCardsInDeck() throws RemoteException {
        if (Client.getInstance().getPlayerList().size()==3 ) {
            deckLabel.setText("30");
        }
        else if (Client.getInstance().getPlayerList().size()==4) {
            deckLabel.setText("28");
        }
        else if (Client.getInstance().getPlayerList().size()==2)
            deckLabel.setText("34");
    }

    private void addFirstListener(){
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

    private void chooseCard (MouseEvent mouseEvent, int idCard) throws RemoteException {
        gameGui=Client.getInstance().getGameGui();
        gameGui.insertCard(Client.getInstance().getUsername(),cardPathLoader.getPath(Client.getInstance().getHand().get(idCard).getId()));
        Client.getInstance().playCard(Client.getInstance().getHand().get(idCard),idCard);
        deleteCorrectCard(idCard);
    }

    private void deleteCorrectCard(int idCard) {
        if (idCard==0) myLeft.setImage(null);
        else if (idCard==1) myCenter.setImage(null);
        else myRight.setImage(null);
    }

    private void initializeAdv1(String p) throws RemoteException {
        nameAdv1.setText(p);
        for (Map.Entry<String, String> entry : Client.getInstance().getPlayerInterface().getplayersTeam().entrySet()) {
            if (entry.getKey().equals(p)) {
                teamAdv1.setText(entry.getValue());
            }
        }
        adv1Left.setImage(new Image(getClass().getResourceAsStream("../Resources/retroCarta.png")));
        adv1Center.setImage(new Image(getClass().getResourceAsStream("../Resources/retroCarta.png")));
        adv1Right.setImage(new Image(getClass().getResourceAsStream("../Resources/retroCarta.png")));
    }

    private void initializeAdv3(String p) throws RemoteException {
        nameAdv3.setText(p);
        for (Map.Entry<String, String> entry : Client.getInstance().getPlayerInterface().getplayersTeam().entrySet()) {
            if (entry.getKey().equals(p)) {
                teamAdv3.setText(entry.getValue());
            }
        }
        adv3Left.setImage(new Image(getClass().getResourceAsStream("../Resources/retroCarta.png")));
        adv3Center.setImage(new Image(getClass().getResourceAsStream("../Resources/retroCarta.png")));
        adv3Right.setImage(new Image(getClass().getResourceAsStream("../Resources/retroCarta.png")));
    }

    private void initializeAdv4(String p) throws RemoteException {
        nameAdv4.setText(p);
        for (Map.Entry<String, String> entry : Client.getInstance().getPlayerInterface().getplayersTeam().entrySet()) {
            if (entry.getKey().equals(p)) {
                teamAdv4.setText(entry.getValue());
            }
        }
        adv4Left.setImage(new Image(getClass().getResourceAsStream("../Resources/retroCarta.png")));
        adv4Center.setImage(new Image(getClass().getResourceAsStream("../Resources/retroCarta.png")));
        adv4Right.setImage(new Image(getClass().getResourceAsStream("../Resources/retroCarta.png")));
    }

    private void initializeOtherAdv() {
        try {
            if (Client.getInstance().getPlayerInterface().getNumPlayersInGame()>2) {
                initializeAdv3(playerList.get(1));
                if (Client.getInstance().getPlayerInterface().getNumPlayersInGame()==4){
                    initializeAdv4(playerList.get(2));
                }
            }
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

}