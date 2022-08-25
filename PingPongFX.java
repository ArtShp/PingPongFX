import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PingPongFX extends Application {
    private static final int SCREEN_WIDTH = 900;
    private static final int SCREEN_HEIGHT = 600;
    private static final int FPS = 60;

    private Label score;
    private BallPane ballPane;
    private PlayerPane player1;
    private PlayerPane player2;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        score = new Label();
        score.setFont(new Font(100));
        score.setTextFill(Color.WHITE);

        ballPane = new BallPane();
        ballPane.setScoreLabel(score);

        player1 = new PlayerPane(true);
        player2 = new PlayerPane(false);

        BorderPane root = new BorderPane();
        Scene scene = new Scene(root, SCREEN_WIDTH, SCREEN_HEIGHT);

        root.setCenter(ballPane);
        root.setLeft(player1);
        root.setRight(player2);

        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.W) {
                player1.setGoUp(true);
            } else if (e.getCode() == KeyCode.S) {
                player1.setGoUp(false);
            }
            if (e.getCode() == KeyCode.UP) {
                player2.setGoUp(true);
            } else if (e.getCode() == KeyCode.DOWN) {
                player2.setGoUp(false);
            }
        });

        scene.setFill(Color.BLACK);
        root.setBackground(Background.EMPTY);

        primaryStage.setTitle("Ping Pong");
        primaryStage.setScene(scene);
        primaryStage.show();

        restart();

        player1.init();
        player2.init();

        update();
    }

    void update() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1000.0/FPS), event -> {
            ballPane.update();
            player1.update();
            player2.update();

            if (ballPane.getX()-ballPane.getRadius() <= 0) {
                if (ballPane.getY() >= player1.getY() &&
                        ballPane.getY() <= player1.getY() + player1.getRacketHeight()) {
                    ballPane.onBounce();
                } else {
                    player2.win();
                    restart();
                }
            }

            if (ballPane.getX()+ballPane.getRadius() >= ballPane.getWidth()) {
                if (ballPane.getY() >= player2.getY() &&
                        ballPane.getY() <= player2.getY() + player2.getRacketHeight()) {
                    ballPane.onBounce();
                } else {
                    player1.win();
                    restart();
                }
            }
        }));
        timeline.setCycleCount(-1);
        timeline.play();
    }

    void restart() {
        score.setText(String.format("%d - %d\n", player1.getWinsCounter(), player2.getWinsCounter()));
        ballPane.init();
        //player1.init();
        //player2.init();
    }
}