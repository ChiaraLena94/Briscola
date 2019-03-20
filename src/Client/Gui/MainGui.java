package Client.Gui;

import Core.Card;
import api.ClientInterface;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.rmi.RemoteException;
import java.util.List;

public class MainGui extends Application implements ClientInterface {
    Stage primaryStage;
    private MainGui mainGui;

    public MainGui getMainGui() {
        return mainGui;
    }

    public void setMainGui(MainGui mainGui) {
        this.mainGui = mainGui;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainGui=new MainGui();
        this.primaryStage=primaryStage;
        this.primaryStage.setTitle("Briscola");
        FXMLLoader loader = new FXMLLoader();
        //AGGIUNGERE FILE FXML
        loader.setLocation(this.getClass().getResource(""));
        Parent root = loader.load();

        Scene scene=new Scene(root);
        primaryStage.setScene(scene);

        Controller controller=loader.getController();
        controller.setMainGui(this);
        primaryStage.show();

    }

    @Override
    public void receiveHand(List<Card> hand) throws RemoteException {

    }

    @Override
    public void selectCard() throws RemoteException {

    }

    @Override
    public void drawCard() throws RemoteException {

    }

    @Override
    public void notifyEndGame(int isWinner) throws RemoteException {

    }
}
