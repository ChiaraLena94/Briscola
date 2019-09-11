package Client.Gui;

import Client.Client;
import Client.Gui.animations.ScaleAnimation;
import Client.Gui.animations.TranslateAnimation;
import Core.Card;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class GameGui {
    private Scene scene;
    private Stage stage;

    private ImageView myLeft =new ImageView();
    private ImageView myCenter =new ImageView();
    private ImageView myRight =new ImageView();
    private ImageView adv1Right =new ImageView();
    private ImageView adv1Center =new ImageView();
    private ImageView adv1Left =new ImageView();
    private ImageView adv3Right =new ImageView();
    private ImageView adv3Center =new ImageView();
    private ImageView adv3Left =new ImageView();
    private ImageView adv4Right =new ImageView();
    private ImageView adv4Center =new ImageView();
    private ImageView adv4Left =new ImageView();
    private ImageView cardPlayer1 =new ImageView();
    private ImageView cardPlayer2 =new ImageView();
    private ImageView cardPlayer3 =new ImageView();
    private ImageView cardPlayer4 =new ImageView();
    private Label myName = new Label();
    private Label deckLabel = new Label();
    private Label myPoints = new Label();
    private Label nameAdv1 = new Label();
    private Label nameAdv3 = new Label();
    private Label nameAdv4 = new Label();
    private Label teamAdv1 = new Label();
    private Label teamAdv3 = new Label();
    private Label teamAdv4 = new Label();
    private Label controlLabel = new Label();
    private ImageView deckAdv1 =new ImageView();
    private ImageView deckAdv3 =new ImageView();
    private ImageView deckAdv4 =new ImageView();
    private ImageView deck =new ImageView();
    private ImageView briscola =new ImageView();
    private ImageView cardAnimation1 = new ImageView();
    private ImageView cardAnimation2 = new ImageView();
    private ImageView cardAnimation3 = new ImageView();
    private ImageView cardAnimation4 = new ImageView();
    private ImageView myDeck =new ImageView();
    private CardPathLoader cardPathLoader;
    private Parent root;
    private List<String> playerList = new ArrayList<>();
    private Map<String, Integer> advMap = new HashMap();
    private ScaleAnimation scaleUp;
    private ScaleAnimation scaleDown;

    //getter methods
    public Stage getStage() {
        return stage;
    }

    public Label getControlLabel() {
        return controlLabel;
    }

    public ImageView getDeck() {
        return deck;
    }

    public ImageView getBriscola() {
        return briscola;
    }

    public Label getDeckLabel() {
        return deckLabel;
    }


    //GameGui constructor
    public GameGui() throws IOException {
        Parent window = createContent();
        Platform.runLater(()-> {
            stage = MainGui.getPrimaryStage();
            scene= new Scene(window);
            stage.setScene(scene);
            stage.centerOnScreen();
            stage.setTitle(Client.getInstance().getUsername());
            stage.show();
            initializeId();
        });
        cardPathLoader=new CardPathLoader();
        initializeAdvMap();
    }

    private void initializeAdvMap() {
        try {
            playerList = Client.getInstance().getPlayerList();
                try{
                    if (Client.getInstance().getPlayerList().size()==2) {
                        playerList.remove(Client.getInstance().getUsername());
                        advMap.put(playerList.get(0),1);
                    }
                    else if (Client.getInstance().getPlayerList().size()==4)
                        initialize4AdvMap(playerList);
                    else {
                        initialize3AdvMap(playerList);
                    }
                }catch (IndexOutOfBoundsException n){
                }
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    private void initialize3AdvMap(List<String> playerList) {
        int pos=10;
        playerList.add(playerList.get(0));
        playerList.add(playerList.get(1));
        for(int i=0; i<playerList.size()-2; i++){
            if (Client.getInstance().getUsername().equals(playerList.get(i))) {
                pos=i;
            }
        }
        switch(pos) {
            case 0:
                advMap.put(playerList.get(1),3);
                advMap.put(playerList.get(2),1);
                break;
            case 1:
                advMap.put(playerList.get(0),1);
                advMap.put(playerList.get(2),3);
                break;
            case 2:
                advMap.put(playerList.get(0),3);
                advMap.put(playerList.get(1),1);
                break;
        }
    }

    private void initialize4AdvMap(List<String> playerList) {
        int pos=10;
        playerList.add(playerList.get(0));
        playerList.add(playerList.get(1));
        playerList.add(playerList.get(2));
        for(int i=0; i<playerList.size()-3; i++){
            if (Client.getInstance().getUsername().equals(playerList.get(i))) {
                pos=i;
            }
        }
        switch(pos) {
            case 0:
                advMap.put(playerList.get(1),3);
                advMap.put(playerList.get(2),1);
                advMap.put(playerList.get(3),4);
                break;

            case 1:
                advMap.put(playerList.get(0),4);
                advMap.put(playerList.get(2),3);
                advMap.put(playerList.get(3),1);
                break;

            case 2:
                advMap.put(playerList.get(0),1);
                advMap.put(playerList.get(1),4);
                advMap.put(playerList.get(3),3);
                break;

            case 3:
                advMap.put(playerList.get(0),3);
                advMap.put(playerList.get(1),1);
                advMap.put(playerList.get(2),4);
                break;
        }
    }

    private void initializeId() {
        myLeft= (ImageView) scene.lookup("#myLeft");
        myCenter= (ImageView) scene.lookup("#myCenter");
        myRight= (ImageView) scene.lookup("#myRight");
        adv1Right= (ImageView) scene.lookup("#adv1Right");
        adv1Center= (ImageView) scene.lookup("#adv1Center");
        adv1Left= (ImageView) scene.lookup("#adv1Left");
        adv3Right= (ImageView) scene.lookup("#adv3Right");
        adv3Center= (ImageView) scene.lookup("#adv3Center");
        adv3Left= (ImageView) scene.lookup("#adv3Left");
        adv4Right= (ImageView) scene.lookup("#adv4Right");
        adv4Center= (ImageView) scene.lookup("#adv4Center");
        adv4Left= (ImageView) scene.lookup("#adv4Left");
        cardPlayer1= (ImageView) scene.lookup("#cardPlayer1");
        cardPlayer2= (ImageView) scene.lookup("#cardPlayer2");
        cardPlayer3= (ImageView) scene.lookup("#cardPlayer3");
        cardPlayer4= (ImageView) scene.lookup("#cardPlayer4");
        cardAnimation1= (ImageView) scene.lookup("#cardAnimation1");
        cardAnimation2= (ImageView) scene.lookup("#cardAnimation2");
        cardAnimation3= (ImageView) scene.lookup("#cardAnimation3");
        cardAnimation4= (ImageView) scene.lookup("#cardAnimation4");
        myDeck= (ImageView) scene.lookup("#myDeck");
        nameAdv1= (Label) scene.lookup("#nameAdv1");
        nameAdv3= (Label) scene.lookup("#nameAdv3");
        nameAdv4= (Label) scene.lookup("#nameAdv4");
        deckAdv1= (ImageView) scene.lookup("#deckAdv1");
        deckAdv3= (ImageView) scene.lookup("#deckAdv3");
        deckAdv4= (ImageView) scene.lookup("#deckAdv4");
        myPoints= (Label) scene.lookup("#myPoints");
        controlLabel= (Label) scene.lookup("#controlLabel");
        deck= (ImageView) scene.lookup("#deck");
        briscola= (ImageView) scene.lookup("#briscola");
        deckLabel= (Label) scene.lookup("#deckLabel");
        teamAdv1= (Label) scene.lookup("#teamAdv1");
        teamAdv3= (Label) scene.lookup("#teamAdv3");
        teamAdv4= (Label) scene.lookup("#teamAdv4");
    }

    public Parent createContent() throws IOException {
        root = FXMLLoader.load(getClass().getResource("../Gui/fxml/game.fxml"));
        return root;
    }

    public void addCardToBoard(String lastPlayer, int idCard) throws RemoteException {
        int last=0;
        insertCard(lastPlayer,cardPathLoader.getPath(idCard));
        last=advMap.get(lastPlayer);

        switch (last){
            case 1:
                if (adv1Right.getImage()==null) {
                    if (adv1Center.getImage()==null) {
                        adv1Left.setImage(null);
                    }
                    else adv1Center.setImage(null);
                }else adv1Right.setImage(null);
                break;
            case 3:
                if (adv3Right.getImage()==null) {
                    if (adv3Center.getImage()==null) {
                        adv3Left.setImage(null);
                    }
                    else adv3Center.setImage(null);
                }
                else adv3Right.setImage(null);
                break;
            case 4:
                if (adv4Right.getImage()==null) {
                    if (adv4Center.getImage()==null) {
                        adv4Left.setImage(null);
                    }
                    else adv4Center.setImage(null);
                }
                else adv4Right.setImage(null);
                break;
            default:
                System.out.println("non dovrei essere qui!");
                break;
        }
    }

    public void insertCard(String lastPlayer, String image) throws RemoteException {
        if (cardPlayer1.getImage() == null) {
            cardPlayer1.setImage(new Image(getClass().getResourceAsStream("../Gui/Resources/" + image)));
        } else {
            if (cardPlayer2.getImage() == null) {
                cardPlayer2.setImage(new Image(getClass().getResourceAsStream("../Gui/Resources/" + image)));
            } else {
                if (cardPlayer3.getImage() == null) {
                    cardPlayer3.setImage(new Image(getClass().getResourceAsStream("../Gui/Resources/" + image)));
                } else {
                    cardPlayer4.setImage(new Image(getClass().getResourceAsStream("../Gui/Resources/" + image)));
                }
            }
        }

    }

    public void endTurn(String turnWinnerPlayer) throws RemoteException {
        animateCardsWinner(turnWinnerPlayer);
    }

    private void animateCardsWinner(String winner) throws RemoteException {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        cardAnimation1.setX(cardPlayer1.getX());
        cardAnimation1.setY(cardPlayer1.getY());
        setEqualsCardId();
        cardAnimation1.setImage(cardPlayer1.getImage());
        cardAnimation2.setImage(cardPlayer2.getImage());
        cardAnimation3.setImage(cardPlayer3.getImage());
        cardAnimation4.setImage(cardPlayer4.getImage());
        deleteCardInTurn();
        new TranslateAnimation(cardAnimation2, cardAnimation1.getX(), cardAnimation1.getY(), Duration.millis(50000.0)).playAnimation();

        if (Client.getInstance().getPlayerList().size()>2) {
            new TranslateAnimation(cardAnimation3, cardAnimation1.getX(), cardAnimation1.getY(), Duration.millis(5000.0)).playAnimation();
            new TranslateAnimation(cardAnimation4, cardAnimation1.getX(), cardAnimation1.getY(), Duration.millis(5000.0)).playAnimation();
        }

        cardAnimation2.setImage(null);
        cardAnimation3.setImage(null);
        cardAnimation4.setImage(null);
        setEqualsCardId();

        Platform.runLater(() ->{
            cardAnimation1.setImage(new Image(getClass().getResourceAsStream("../Gui/Resources/retroCarta.png")));
            if(winner.equals(Client.getInstance().getUsername())) {
                new TranslateAnimation(cardAnimation1, myDeck.getX(), myDeck.getY(), Duration.millis(1000)).playAnimation();
                myDeck.setImage(new Image(getClass().getResourceAsStream("../Gui/Resources/retroCarta.png")));
            }
            else {
                createAdvDeck(winner);
            }

            cardAnimation1.setImage(null);
            cardAnimation1.setX(cardPlayer1.getX());
            cardAnimation1.setY(cardPlayer1.getY());
            try {
                updateLabelPoints(winner);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });


    }

    private void updateLabelPoints (String winner) throws RemoteException {
        for (Map.Entry<String, Integer> entry : Client.getInstance().getMapPoints().entrySet()) {
           if (Client.getInstance().getPlayerList().size()==4) {
                for (int i=0; i<playerList.size(); i++) {
                    if (winner.equals(entry.getKey()) && winner.equals(playerList.get(i))){
                        if (i>2) {
                            if (( Client.getInstance().getUsername().equals(playerList.get((i-2))))) {
                                myPoints.setText(entry.getValue().toString());
                            }
                        }
                        else if (Client.getInstance().getUsername().equals(playerList.get(i+2))) {
                            myPoints.setText(entry.getValue().toString());
                        }
                    }
                }
            }
            if (winner.equals(entry.getKey()) && winner.equals(Client.getInstance().getUsername())){
                myPoints.setText(entry.getValue().toString());
            }
        }

    }

    private void setEqualsCardId (){
        cardAnimation2.setX(cardPlayer1.getX());
        cardAnimation2.setY(cardPlayer1.getY());
        cardAnimation3.setX(cardPlayer1.getX());
        cardAnimation3.setY(cardPlayer1.getY());
        cardAnimation4.setX(cardPlayer1.getX());
        cardAnimation4.setY(cardPlayer1.getY());
    }

    private void createAdvDeck(String winner){
        if (winner.equals(nameAdv1.getText())){
            new TranslateAnimation(cardAnimation1, deckAdv1.getX(), deckAdv1.getY(), Duration.seconds(1.5)).playAnimation();
            deckAdv1.setImage(new Image(getClass().getResourceAsStream("../Gui/Resources/retroCarta.png")));
        }
        if (winner.equals(nameAdv3.getText())){
            new TranslateAnimation(cardAnimation1, deckAdv3.getX(), deckAdv3.getY(), Duration.seconds(1.5)).playAnimation();
            deckAdv3.setImage(new Image(getClass().getResourceAsStream("../Gui/Resources/retroCarta.png")));
        }

        if (winner.equals(nameAdv4.getText())){
            new TranslateAnimation(cardAnimation1, deckAdv4.getX(), deckAdv4.getY(), Duration.seconds(1.5)).playAnimation();
            deckAdv4.setImage(new Image(getClass().getResourceAsStream("../Gui/Resources/retroCarta.png")));
        }

    }

    private void deleteCardInTurn() {
        if (cardPlayer1 != null) {
            cardPlayer1.setImage(null);
        }
        if (cardPlayer2 != null) {
            cardPlayer2.setImage(null);
        }
        if (cardPlayer3 != null) {
            cardPlayer3.setImage(null);
        }
        if (cardPlayer4 != null) {
            cardPlayer4.setImage(null);
        }
    }

    public void updateHand(Card c) throws RemoteException {
        Platform.runLater(() ->{
            if (myLeft.getImage() == null){
                myLeft.setImage(new Image(getClass().getResourceAsStream("../Gui/Resources/"+cardPathLoader.getPath(c.getId()))));
            }
            if (myCenter.getImage() == null){
                myCenter.setImage(new Image(getClass().getResourceAsStream("../Gui/Resources/"+cardPathLoader.getPath(c.getId()))));
            }
            if (myRight.getImage() == null){
                myRight.setImage(new Image(getClass().getResourceAsStream("../Gui/Resources/"+cardPathLoader.getPath(c.getId()))));
            }
            try {
                updateAdvHand();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });

    }

    private void updateAdvHand() throws RemoteException {
        if (adv1Right.getImage()==null) {
            adv1Right.setImage(new Image(getClass().getResourceAsStream("../Gui/Resources/retroCarta.png")));
        }
        else {
            if (adv1Center.getImage()==null) {
                adv1Center.setImage(new Image(getClass().getResourceAsStream("../Gui/Resources/retroCarta.png")));
            }
            else {
                if (adv1Left.getImage()==null) {
                    adv1Left.setImage(new Image(getClass().getResourceAsStream("../Gui/Resources/retroCarta.png")));
                }
            }
        }
        if (Client.getInstance().getPlayerList().size()>2) {
            updateOtherAdvHands();
        }
    }

    private void updateOtherAdvHands() throws RemoteException {
        if (Client.getInstance().getPlayerList().size()>=3) {
            if (adv3Right.getImage()==null) {
                adv3Right.setImage(new Image(getClass().getResourceAsStream("../Gui/Resources/retroCarta.png")));
            }
            else {
                if (adv3Center.getImage()==null) {
                    adv3Center.setImage(new Image(getClass().getResourceAsStream("../Gui/Resources/retroCarta.png")));
                }
                else {
                    if (adv3Left.getImage()==null) {
                        adv3Left.setImage(new Image(getClass().getResourceAsStream("../Gui/Resources/retroCarta.png")));
                    }
                }
            }
        }

        if (Client.getInstance().getPlayerList().size()==4) {
            if (adv4Right.getImage()==null) {
                adv4Right.setImage(new Image(getClass().getResourceAsStream("../Gui/Resources/retroCarta.png")));
            }
            else {
                if (adv4Center.getImage()==null) {
                    adv4Center.setImage(new Image(getClass().getResourceAsStream("../Gui/Resources/retroCarta.png")));
                }
                else {
                    if (adv4Left.getImage()==null) {
                        adv4Left.setImage(new Image(getClass().getResourceAsStream("../Gui/Resources/retroCarta.png")));
                    }
                }
            }
        }
    }

    public void addListenerToCards(){
        myLeft.setOnMouseClicked(mouseEvent -> {
            try {
                chooseCard(mouseEvent,0);
                changeControl();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });

        myCenter.setOnMouseClicked(mouseEvent -> {
            try {
                chooseCard(mouseEvent,1);
                changeControl();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });

        myRight.setOnMouseClicked(mouseEvent -> {
            try {
                chooseCard(mouseEvent,2);
                changeControl();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        });
    }

    private void changeControl(){
        controlLabel.setText("ASPETTA IL TUO TURNO");
        scaleUp = new ScaleAnimation(controlLabel, 1.3, 1.3, Duration.millis(1000.0));
        scaleDown = new ScaleAnimation(controlLabel, 1, 1, Duration.millis(1000.0));
        scaleUp.playAnimation();
        scaleUp.getScaleTransition().setOnFinished(event -> {
            scaleDown.playAnimation();
        });
        removeCardListener();
    }

    private void removeCardListener() {
        myLeft.setOnMouseClicked(mouseEvent -> {
        });

        myCenter.setOnMouseClicked(mouseEvent -> {
        });

        myRight.setOnMouseClicked(mouseEvent -> {
        });
    }

    private void chooseCard (MouseEvent mouseEvent, int idCard) throws RemoteException {
        try{
            insertCard(Client.getInstance().getUsername(), cardPathLoader.getPath(Client.getInstance().getHand().get(idCard).getId()));
        }catch(NullPointerException e) {
            e.printStackTrace();
        }
        try{
            Client.getInstance().playCard(Client.getInstance().getHand().get(idCard),idCard);
        }catch(RemoteException |NullPointerException e) {
            e.printStackTrace();
        }
        deleteCorrectCard(idCard);
    }

    private void deleteCorrectCard(int idCard) {
        if (idCard==0) myLeft.setImage(null);
        else if (idCard==1) myCenter.setImage(null);
        else if (idCard==2) myRight.setImage(null);
    }
}