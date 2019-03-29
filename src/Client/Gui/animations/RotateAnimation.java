package Client.Gui.animations;

import javafx.animation.RotateTransition;
import javafx.animation.Transition;
import javafx.scene.Node;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class RotateAnimation implements BriscolaAnimation{
    private Node node;
    private RotateTransition rotateTransition;

    public RotateAnimation(Duration duration, Node node){
        this.node = node;
        rotateTransition = new RotateTransition();
        rotateTransition.setNode(node);
        rotateTransition.setDuration(duration);
        rotateTransition.setAutoReverse(true);
        rotateTransition.setByAngle(360);
        rotateTransition.setAxis(Rotate.Z_AXIS);
    }

    public RotateTransition getRotateTransition() {
        return rotateTransition;
    }


    @Override
    public void playAnimation() {
            rotateTransition.play();
    }
}
