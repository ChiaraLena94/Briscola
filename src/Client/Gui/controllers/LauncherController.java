package Client.Gui.controllers;

import Client.Gui.LoginGui;
import Client.Gui.animations.FadeAnimation;
import Client.Gui.animations.RotateAnimation;
import Client.Gui.animations.ScaleAnimation;
import Client.Gui.animations.TranslateAnimation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LauncherController implements Initializable {
    private FadeAnimation fadeIn;
    private TranslateAnimation translateSword, translateCup, translateGold, translateStick;
    private RotateAnimation rotateSword, rotateCup, rotateGold, rotateStick;
    private ScaleAnimation scaleUp,scaleDown;
    private LoginGui loginGui;

    @FXML
    public Button startButton;

    @FXML
    public ImageView startImage;

    @FXML
    public ImageView swordImage;

    @FXML
    public ImageView cupImage;

    @FXML
    public ImageView goldImage;

    @FXML
    public ImageView stickImage;



    //the method initialize is the first method that is called when the stage is open.
    // it launches the animations and sets the cursor
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startButton.setCursor(Cursor.HAND);
        initializeScaleAnimation();
    }

    private void initializeScaleAnimation() {
        scaleDown= new ScaleAnimation(startImage,0.00001, 0.00001, Duration.millis(0.1));
        scaleDown.playAnimation();

        scaleDown.getScaleTransition().setOnFinished(event -> {
            scaleUp = new ScaleAnimation(startImage, 1.0f,1.0f, Duration.seconds(2.5));
            scaleUp.playAnimation();
            initializeFadeAnimation();
        });
    }

    private void initializeFadeAnimation() {
        fadeIn = new FadeAnimation(startImage,0.5,1, Duration.seconds(2.5));
        fadeIn.playAnimation();
        fadeIn.getFadeTransition().setOnFinished(event -> {
            initializeTransitionAnimation();
        });
    }

    private void initializeTransitionAnimation(){
        translateSword = new TranslateAnimation(swordImage,520,-652, Duration.seconds(1.5));
        translateSword.playAnimation();
        rotateSword = new RotateAnimation(Duration.seconds(1.5), swordImage);
        rotateSword.playAnimation();

        translateGold = new TranslateAnimation(goldImage,410,162, Duration.seconds(1.5));
        translateGold.playAnimation();
        rotateGold = new RotateAnimation(Duration.seconds(1.5), goldImage);
        rotateGold.playAnimation();

        translateCup = new TranslateAnimation(cupImage,-410,155, Duration.seconds(1.5));
        translateCup.playAnimation();
        rotateCup = new RotateAnimation(Duration.seconds(1.5), cupImage);
        rotateCup.playAnimation();

        translateStick = new TranslateAnimation(stickImage,-325,-652, Duration.seconds(1.5));
        translateStick.playAnimation();
        rotateStick = new RotateAnimation(Duration.seconds(1.5), stickImage);
        rotateStick.playAnimation();
    }

    public void play (ActionEvent actionEvent) throws IOException {
        loginGui.createLoginGui();
    }



}