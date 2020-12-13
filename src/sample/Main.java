package sample;

import javafx.animation.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import pong.Impact;
import pong.PongGame;
import pong.Net;
import sample.view.DrawPong;
import sample.view.GameScenes;

//import java.awt.*;

/**
 * Contains the view and controller options for Pong Game interactions
 *
 * @author Lindsay Kerr
 * @version 0.1
 */
public class Main extends Application {

    int screenWidth;
    int screenHeight;
    int gameAreaWidth;
    int gameAreaHeight;
    PongGame gameLogic;
    DrawPong gameGraphics;
    Scene rootScene;
    Scene gameScene;
    double step;


    @Override
    public void init(){
        screenWidth = 800;
        screenHeight = 600;
        gameAreaWidth = screenWidth;
        gameAreaHeight = 500;
        gameLogic = new PongGame(gameAreaWidth, gameAreaHeight,true);
        gameGraphics = new DrawPong(
                new Canvas(gameAreaWidth, gameAreaHeight),
                gameLogic,
                Color.BLUE,
                Color.WHITE
        );
        recalculateStep();
        rootScene = GameScenes.startScene(800, 600);
        gameScene = GameScenes.gameScene(
                (int) rootScene.getWidth(),
                (int) rootScene.getHeight(),
                gameGraphics.getCanvas()
        );



    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Pong Clone");
        primaryStage.setScene(rootScene);

        rootScene.addEventFilter(KeyEvent.KEY_PRESSED, gameStartKeys -> {
            KeyCode code = gameStartKeys.getCode();

            if (code == KeyCode.ENTER) {
                gameLogic.start();
                primaryStage.setScene(gameScene);


            }
            if (code == KeyCode.ESCAPE) {
                primaryStage.close();
            }

        });

        gameScene.addEventFilter(KeyEvent.KEY_PRESSED, gamePlayKeys -> {
            KeyCode code = gamePlayKeys.getCode();
            if (code == KeyCode.ESCAPE) {
                primaryStage.close();
            }
            if (code == KeyCode.UP) {
                gameLogic.players[0].paddle.moveUp();
            }

            if (code == KeyCode.DOWN) {
                gameLogic.players[0].paddle.moveDown();
            }

        } );


        //primaryStage.getChildren().add(graphics.getCanvas());



        Timeline gameloop = new Timeline();

        /* loop forever */
        gameloop.setCycleCount(Timeline.INDEFINITE);

        /* prevent reverse animation */
        gameloop.setAutoReverse(false);

        /* implements the game loop */
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.01), new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent ae) {
                if (gameLogic.isActive) {

                    Impact aBallImpact = gameLogic.isThereABallImpact(
                            gameLogic.ball.getXPos(),
                            gameLogic.ball.getYPos()
                    );

                    if (aBallImpact != null) {

                        if (aBallImpact.toString().equals("net")) {
                            gameLogic.registerScore((Net) aBallImpact);
                            gameLogic.serveBall();

                        } else {
                            recalculateStep();
                            gameLogic.ball.impact(aBallImpact);
                            gameLogic.ball.increaseSpeed(5);
                        }
                    }

                    if (gameLogic.ball.isWithinBounds()) {

                        gameGraphics.render();
                        //gameLogic.ball.printVariables();
                    }
                    else {
                        gameLogic.ball.nextMove();
                    }
                    changeBallPos();
                }
            }
        });

        gameloop.getKeyFrames().add(keyFrame);
        primaryStage.show();
        gameloop.play();

    }

    private void changeBallPos() {
        gameLogic.ball.linearStep(step);
    }

    private void recalculateStep() {
        step = gameAreaWidth / (300 - gameLogic.ball.getSpeed());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
