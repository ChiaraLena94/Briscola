package Client.Gui.controllers;

import Client.Client;
import Client.Gui.MainGui;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    int numPlayers =0;
    Client clientBriscola;
    Stage waitStage;

    @FXML
    public Button loginButton;

    @FXML
    public TextField username;

    @FXML
    public RadioButton choice2;

    @FXML
    public RadioButton choice3;

    @FXML
    public RadioButton choice4;

    @FXML
    public ToggleGroup group;



    public void login(ActionEvent actionEvent) throws IOException {
        if (numPlayers==2 || numPlayers==3 || numPlayers==4) {
            username.getText();
            System.out.println("sono dentro il login in loginController. numPlayers è: " + numPlayers);
            System.out.println("sono in login in logincontroller e username è: " + username.getText());
            if (usernameEmpty()) {
                return;
            }
            else {
                System.out.println("sono dentro il login in loginController numPlayers è: " + numPlayers);
                try {
                    clientBriscola = Client.createInstance(username.getText());
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                clientBriscola.login(username.getText(), numPlayers);
                Parent root = FXMLLoader.load(getClass().getResource("../fxml/wait.fxml"));
                waitStage = new Stage();
                waitStage.setScene(new Scene(root, 800, 600));
                waitStage.centerOnScreen();
                clientBriscola.setBufferStage(waitStage);
                MainGui.getPrimaryStage().close();
                waitStage.show();
            }

        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("ERROR");
            alert.setContentText("You have to choose the number of players first!");
            alert.showAndWait();
        }
    }

    private boolean usernameEmpty() {
        System.out.println("sono in username empty e username è: " + username.getText());
        if(username.getText().equals(null))
            return true;
        return false;
    }


        @Override
    public void initialize(URL location, ResourceBundle resources) {
    }


    public void submitChoice (ActionEvent actionEvent) {
        group = new ToggleGroup();
        choice2.setToggleGroup(group);
        choice3.setToggleGroup(group);
        choice4.setToggleGroup(group);

        if (choice2.isSelected()) {
            numPlayers = 2;
            System.out.println("hai selezionato " + group.getSelectedToggle());
        } else if (choice3.isSelected()) {
            numPlayers = 3;
            System.out.println("hai selezionato " + group.getSelectedToggle());
        } else if (choice4.isSelected()) {
            numPlayers = 4;
            System.out.println("hai selezionato " + group.getSelectedToggle());
        }
    }
}
