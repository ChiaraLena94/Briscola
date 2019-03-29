package Client.Gui.animations;

import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.util.Duration;

public class TranslateAnimation implements BriscolaAnimation{
   private Node node;
   private TranslateTransition translateTransition;

        public TranslateAnimation(Node node, double toX, double toY, Duration duration){
            this.node = node;
            translateTransition = new TranslateTransition();
            translateTransition.setNode(node);
            translateTransition.setToX(toX);
            translateTransition.setToY(toY);
            translateTransition.setDuration(duration);
        }

    public TranslateTransition getTranslateTransition() {
        return translateTransition;
    }

   @Override
   public void playAnimation(){
            translateTransition.play();
        }

}
