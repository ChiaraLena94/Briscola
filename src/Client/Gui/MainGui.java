package Client.Gui;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainGui extends Application {
    private static Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage=primaryStage;
        Parent root = FXMLLoader.load(getClass().getResource("fxml/launcher.fxml"));
        primaryStage.setTitle("Briscola");
        primaryStage.setScene(new Scene(root, 800, 600));

        primaryStage.centerOnScreen();
        //TUTTE LE OPZIONI DI GRAFICA
        primaryStage.show();

    }

    public static Stage getPrimaryStage(){
        return primaryStage;
    }
}
