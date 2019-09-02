package Client.Gui;

import Client.Client;
import Client.Gui.animations.TranslateAnimation;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import javax.swing.*;
import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Map;

import static sun.audio.AudioPlayer.player;

public class GameGui {
    private Scene scene;
    private Stage stage;
    private ImageView myLeft =new ImageView();
    private ImageView myCenter =new ImageView();
    private ImageView myRight =new ImageView();
    private ImageView adv1Right =new ImageView();
    private ImageView cardPlayer1 =new ImageView();
    private ImageView cardPlayer2 =new ImageView();
    private ImageView cardPlayer3 =new ImageView();
    private ImageView cardPlayer4 =new ImageView();
    private Label myName = new Label();
    private Label myPoints = new Label();
    private Label nameAdv1 = new Label();
    private Label nameAdv3 = new Label();
    private Label nameAdv4 = new Label();
    private ImageView deckAdv1 =new ImageView();
    private ImageView deckAdv3 =new ImageView();
    private ImageView deckAdv4 =new ImageView();
    private ImageView cardAnimation1 = new ImageView();
    private ImageView cardAnimation2 = new ImageView();
    private ImageView cardAnimation3 = new ImageView();
    private ImageView cardAnimation4 = new ImageView();
    private ImageView myDeck =new ImageView();
    private CardPathLoader cardPathLoader;
    private Parent root;

    //getter methods
    public Stage getStage() {
        return stage;
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
            });
        System.out.println("sono in gamegui e lo stage è "+stage);
        cardPathLoader=new CardPathLoader();
    }


    private void initializeId() {
        myLeft= (ImageView) scene.lookup("#myLeft");
        myCenter= (ImageView) scene.lookup("#myCenter");
        myRight= (ImageView) scene.lookup("#myRight");
        adv1Right= (ImageView) scene.lookup("#adv1Right");
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
    }

    public Parent createContent() throws IOException {
        root = FXMLLoader.load(getClass().getResource("../Gui/fxml/game.fxml"));
        return root;
    }

    public void addCardToBoard(int idCard) {
        insertCard(cardPathLoader.getPath(idCard));
        adv1Right.setImage(null);
    }

    public void insertCard (String image) {
        initializeId();
        System.out.println("printo cardPlayer1" +cardPlayer1);
        if (cardPlayer1.getImage()==null) {
            cardPlayer1.setImage(new Image(getClass().getResourceAsStream("../Gui/Resources/"+image)));
        }
        else if (cardPlayer2.getImage()==null) {
            cardPlayer2.setImage(new Image(getClass().getResourceAsStream("../Gui/Resources/"+image)));
        }
        else if (cardPlayer3.getImage()==null) {
            cardPlayer3.setImage(new Image(getClass().getResourceAsStream("../Gui/Resources/"+image)));
        }
        else{
            cardPlayer4.setImage(new Image(getClass().getResourceAsStream("../Gui/Resources/"+image)));
        }
    }

    public void endTurn(String turnWinnerPlayer) throws RemoteException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("TURN WINNER");
        alert.setContentText("The winner of this turn is "+turnWinnerPlayer);
        alert.showAndWait();
        animateCardsWinner(turnWinnerPlayer);
    }

    private void animateCardsWinner(String winner) throws RemoteException {
        cardAnimation1.setX(cardPlayer1.getX());
        cardAnimation1.setY(cardPlayer1.getY());
        setEqualsCardId();
        cardAnimation1.setImage(cardPlayer1.getImage());
        cardAnimation2.setImage(cardPlayer2.getImage());
        cardAnimation3.setImage(cardPlayer3.getImage());
        cardAnimation4.setImage(cardPlayer4.getImage());

        deleteCardInTurn();

        new TranslateAnimation(cardAnimation2, cardAnimation1.getX(), cardAnimation1.getY(), Duration.seconds(2.5)).playAnimation();
        new TranslateAnimation(cardAnimation3, cardAnimation1.getX(), cardAnimation1.getY(), Duration.seconds(2.5)).playAnimation();
        new TranslateAnimation(cardAnimation4, cardAnimation1.getX(), cardAnimation1.getY(), Duration.seconds(2.5)).playAnimation();
        cardAnimation1.setImage(new Image(getClass().getResourceAsStream("../Gui/Resources/retroCarta.png")));
        cardAnimation2.setImage(null);
        cardAnimation3.setImage(null);
        cardAnimation4.setImage(null);
        setEqualsCardId();

        if(winner.equals(Client.getInstance().getUsername())) {
            new TranslateAnimation(cardAnimation1, myDeck.getX(), myDeck.getY(), Duration.seconds(1.5)).playAnimation();
            myDeck.setImage(new Image(getClass().getResourceAsStream("../Gui/Resources/retroCarta.png")));
        }
        else {
            createAdvDeck(winner);
        }

        cardAnimation1.setImage(null);
        cardAnimation1.setX(cardPlayer1.getX());
        cardAnimation1.setY(cardPlayer1.getY());
        updateLabelPoints(winner);
    }

    private void updateLabelPoints (String winner) throws RemoteException {
        for (Map.Entry<String, Integer> entry : Client.getInstance().getMapPoints().entrySet()) {
            if (winner.equals(entry.getKey()) && winner.equals(Client.getInstance().getUsername())){
                myPoints.setText(entry.getValue().toString());
                System.out.println("i punti sono: "+(entry.getValue().toString()));
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
        System.out.println("la carta si sposta verso il vincitore");
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
}
