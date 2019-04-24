package Client.Gui.controllers;


import Client.Gui.animations.RotateAnimation;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.awt.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;

public class WaitController implements Initializable {

    RotateAnimation rotateBuffer;

    @FXML
    public ImageView buffer;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        rotateBuffer = new RotateAnimation(Duration.seconds(1.5), buffer);
        rotateBuffer.getRotateTransition().setByAngle(360);
        rotateBuffer.getRotateTransition().setAutoReverse(false);
        rotateBuffer.getRotateTransition().setCycleCount(100);
        rotateBuffer.playAnimation();
    }

}
