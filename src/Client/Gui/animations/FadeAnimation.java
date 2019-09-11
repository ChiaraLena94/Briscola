package Client.Gui.animations;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.util.Duration;


public class FadeAnimation implements BriscolaAnimation{

    private Node node;
    private FadeTransition fadeTransition;


    //constructor
    public FadeAnimation(Node node, double from, double to, Duration duration) {
        this.node = node;
        fadeTransition = new FadeTransition();
        fadeTransition.setNode(node);
        fadeTransition.setFromValue(from);
        fadeTransition.setToValue(to);
        fadeTransition.setDuration(duration);
    }

    //getter method
    public FadeTransition getFadeTransition() {
        return fadeTransition;
    }

    @Override
    public void playAnimation() {
        fadeTransition.play();
    }
}