package Client.Gui;

import Client.Client;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class GameGui {
    private Scene scene;
    private Stage stage;
    private ImageView myLeft =null;
    private ImageView myCenter =null;
    private ImageView myRight =null;
    private ImageView cardPlayer1 =new ImageView();
    private ImageView cardPlayer2 =new ImageView();
    private ImageView cardPlayer3 =new ImageView();
    private ImageView cardPlayer4 =new ImageView();
    private CardPathLoader cardPathLoader;


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
        cardPlayer1= (ImageView) scene.lookup("#cardPlayer1");
        System.out.println("sono in initializeId e printo crdPlayer1"+cardPlayer1);
        cardPlayer2= (ImageView) scene.lookup("#cardPlayer2");
        cardPlayer3= (ImageView) scene.lookup("#cardPlayer3");
        cardPlayer4= (ImageView) scene.lookup("#cardPlayer4");
    }

    public Parent createContent() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("../Gui/fxml/game.fxml"));
        return root;
    }



    public void addCardToBoard(int idCard) {
        insertCard(cardPathLoader.getPath(idCard));

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






    public Stage getStage() {
        return stage;
    }
}
