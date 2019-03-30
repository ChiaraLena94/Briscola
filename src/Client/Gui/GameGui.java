package Client.Gui;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class GameGui {
    private Pane root;


    public GameGui() {
        this.root =new Pane();
    }

    /**
     * metodo che crea il Pane del login e scelta del gioco, e viene richiamato nel metodo createGameGui, per poi passarlo nella
     * funzione Lambda Platform.runLater() -> e farlo diventare la nuova Scene
     */

    public Parent createContent() throws IOException {
        //chiamo fxml della seconda schermata
        Parent root = FXMLLoader.load(getClass().getResource("fxml/login.fxml"));
        return root;
    }


    public static void createGameGui() throws IOException {
        GameGui gameGui =new GameGui();
        Parent window = gameGui.createContent();
        Platform.runLater(()->{
            Stage stage = MainGui.getPrimaryStage();
            Scene scene= new Scene(window);
            stage.setScene(scene);
            stage.centerOnScreen();
        });
    }

}
