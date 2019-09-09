package Client.Gui;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginGui {
    private Pane root;


    public LoginGui() {
        this.root =new Pane();
    }

    /**
     * metodo che crea il Pane del login e scelta del gioco, e viene richiamato nel metodo createLoginGui, per poi passarlo nella
     * funzione Lambda Platform.runLater() -> e farlo diventare la nuova Scene
     */

    public Parent createContent() throws IOException {
        //chiamo fxml della seconda schermata
        Parent root = FXMLLoader.load(getClass().getResource("fxml/login.fxml"));
        return root;
    }


    public static void createLoginGui() throws IOException {
        LoginGui loginGui =new LoginGui();
        Parent window = loginGui.createContent();
        Platform.runLater(()->{
            Stage stage = MainGui.getPrimaryStage();
            Scene scene= new Scene(window);
            stage.setScene(scene);
            stage.centerOnScreen();
        });
    }

}
