package Client.Gui;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginGui {
    private Pane root;

    //Method constructor
    public LoginGui() {
        this.root =new Pane();
    }


    public Parent createContent() throws IOException {
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
