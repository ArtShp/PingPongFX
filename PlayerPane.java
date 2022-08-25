import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class PlayerPane extends Pane {
    private static final double BASIC_SPEED = 5;

    private static final double RACKET_WIDTH = 15;
    private static final double RACKET_HEIGHT = 150;

    private final boolean basicGoUp;

    private boolean goUp;
    private double speed;
    private int winsCounter;

    private Rectangle racket;

    PlayerPane(boolean basicGoUp) {
        winsCounter = 0;
        this.basicGoUp = basicGoUp;

        racket = new Rectangle();
        getChildren().add(racket);
    }

    void init() {
        racket.setWidth(RACKET_WIDTH);
        racket.setHeight(RACKET_HEIGHT);
        racket.setFill(Color.DODGERBLUE);

        racket.setLayoutY(getHeight()/2);
        goUp = basicGoUp;
        speed = BASIC_SPEED;
    }

    void update() {
        if (racket.getLayoutY() < 0 || racket.getLayoutY() > getHeight()-racket.getHeight()) {
            goUp = !goUp;
        }

        if (goUp) racket.setLayoutY(racket.getLayoutY() - speed);
        else racket.setLayoutY(racket.getLayoutY() + speed);
    }

    void setGoUp(boolean goUp) {
        this.goUp = goUp;
    }

    void win() {winsCounter++;}
    int getWinsCounter() {return winsCounter;}

    double getY() {return racket.getLayoutY();}
    double getRacketHeight() {return racket.getHeight();}
}
