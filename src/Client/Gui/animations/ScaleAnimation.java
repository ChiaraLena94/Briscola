package Client.Gui.animations;

import javafx.animation.ScaleTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class ScaleAnimation implements BriscolaAnimation{
    private Node node;
    private ScaleTransition scaleTransition;
    private Duration duration;

    public ScaleAnimation(Node node, double x, double y, Duration duration){
        this.node = node;
        this.duration = duration;
        scaleTransition = new ScaleTransition();
        scaleTransition.setDuration(duration);
        scaleTransition.setNode(node);
        scaleTransition.setToX(x);
        scaleTransition.setToY(y);
    }

    public ScaleTransition getScaleTransition() {
        return scaleTransition;
    }


    @Override
    public void playAnimation() {
        scaleTransition.play();
    }
}
