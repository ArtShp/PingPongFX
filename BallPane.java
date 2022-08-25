import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

class BallPane extends Pane {
    private static final double BASE_SPEED = 5;
    private static final double SPEED_INCREASE_PERCENTS = 5;

    private double deltaX;
    private double deltaY;

    private Circle ball;
    private Label score;

    BallPane() {
        ball = new Circle();
        getChildren().addAll(ball);
    }

    void init() {
        ball.setLayoutX(getWidth()/2);
        ball.setLayoutY(getHeight()/2);

        ball.setRadius(15);
        ball.setFill(Color.PINK);

        deltaX = Math.random() * 4 + 3;
        deltaY = Math.random() * 4 + 2;

        // to be speed equal in different vectors
        double multiplier = BASE_SPEED / Math.sqrt(deltaX*deltaX + deltaY*deltaY);
        deltaX *= multiplier;
        deltaY *= multiplier;

        if (Math.random() < 0.5) deltaX *= -1;
        if (Math.random() < 0.5) deltaY *= -1;
    }

    void update() {
        boolean rightBorder = ball.getLayoutX() >= (getWidth() - ball.getRadius());
        boolean leftBorder = ball.getLayoutX() <= (0 + ball.getRadius());
        boolean bottomBorder = ball.getLayoutY() >= (getHeight() - ball.getRadius());
        boolean topBorder = ball.getLayoutY() <= (0 + ball.getRadius());

        if (rightBorder || leftBorder) deltaX *= -1;
        if (bottomBorder || topBorder) deltaY *= -1;

        ball.setLayoutX(ball.getLayoutX() + deltaX);
        ball.setLayoutY(ball.getLayoutY() + deltaY);

        score.setLayoutX(getWidth()/2 - score.getWidth()/2);
    }

    void onBounce() {
        double increment = 1 + (SPEED_INCREASE_PERCENTS / 100.0);
        deltaX *= increment;
        deltaY *= increment;
    }

    void setScoreLabel(Label score) {
        this.score = score;
        getChildren().add(score);
    }

    double getX() {return ball.getLayoutX();}
    double getY() {return ball.getLayoutY();}
    double getRadius() {return ball.getRadius();}
}