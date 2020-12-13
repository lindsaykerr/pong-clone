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
import utilities.Calculate;

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
    double bX;
    double bY;
    double bG;
    double bYi;
    double ballTravel;
    double spacerFrame = 10;
    Scene rootScene;
    Scene gameScene;


    @Override
    public void init(){
        screenWidth = 800;
        screenHeight = 600;
        gameAreaWidth = 700;
        gameAreaHeight = 550;
        gameLogic = new PongGame(gameAreaWidth, gameAreaHeight,true);

        ballCalculateLinearValues();

        gameGraphics = new DrawPong(
                new Canvas(gameAreaWidth, gameAreaHeight),
                gameLogic,
                Color.BLUE,
                Color.WHITE
        );

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
                gameGraphics.clearGraphics();

                Impact  aBallImpact = gameLogic.isThereABallImpact(bX, bY);
                boolean delay = false;
                if (aBallImpact != null) {

                    if (aBallImpact.toString().equals("net")) {
                        gameLogic.registerScore((Net) aBallImpact);
                        gameLogic.serveBall();
                    }
                    else {
                        gameLogic.pongBall.increaseSpeed(5);
                        gameLogic.pongBall.impact(aBallImpact, (int) bX, (int) bY);
                    }
                    ballCalculateLinearValues();
                }
                if (!(
                        bX > 0 &&
                        bX + (gameLogic.pongBall.getDiameter() / 2.0) < gameAreaWidth &&
                        bY > 0 &&
                        bY + (gameLogic.pongBall.getDiameter() / 2.0) < gameAreaHeight
                )) {
                    gameLogic.pongBall.nextMove();
                    ballCalculateLinearValues();
                }
                if (gameLogic.gameEnds) {
                    gameGraphics.drawGameOver();
                    gameloop.stop();
                }
                else {
                    gameGraphics.drawPaddles();
                    ballChangeXYPos();
                    gameGraphics.drawBall(bX, bY);
                }
            }
        });

        gameloop.getKeyFrames().add(keyFrame);
        primaryStage.show();
        gameloop.play();

    }



    private void ballCalculateLinearValues() {

        bX = gameLogic.pongBall.getPreviousX();
        bY = gameLogic.pongBall.getPreviousY();

        bG = Calculate.gradientOfLine(
                gameLogic.pongBall.getPreviousX(),
                gameLogic.pongBall.getPreviousY(),
                gameLogic.pongBall.getX(),
                gameLogic.pongBall.getY()
        );

        bYi = Calculate.yIntercept(
                bG,
                gameLogic.pongBall.getX(),
                gameLogic.pongBall.getY()
        );

        ballTravel = Calculate.distanceBetweenPoints(
                gameLogic.pongBall.getPreviousX(),
                gameLogic.pongBall.getPreviousY(),
                gameLogic.pongBall.getX(),
                gameLogic.pongBall.getY()
        );
    }

    /*
     * Whenever the game loop is called a new XY position of the ball is required,
     * The XY position is derived from a linear equation, using the precalculated
     * slope and y increment. A progression step (pixels moved) is derived by
     * taking the total distance the ball must travel and multiplying it by a
     * fraction of a second.
     */
    private void ballChangeXYPos()  {
        //System.out.println("travel" + ballTravel + " yinc " + bYi);

        double step = gameAreaWidth / (300 - gameLogic.pongBall.getSpeed());




        // When the slope ratio is greater than |1| the x coordinate of the line is
        // calculated rather than the y as it provides more accurate result.
        if (Math.abs(bG) < 1) {

            // if the ball is moving towards 0 on the x axis, step is negative.
            if (gameLogic.pongBall.getXTravel() == -1) {
                step = -(step);
            }
            bX += step;
            bY = Calculate.yCoordinate(bG, bX, bYi);
        }
        else {
            // if the ball is moving towards 0 on the y axis, step is negative.
            if (gameLogic.pongBall.getYTravel() == -1) {
                step = -(step);
            }
            bY += step;
            bX = Calculate.xCoordinate(bG, bY, bYi);
        }
        //System.out.println("x" + bX + " y" +bY);


    }

    public static void main(String[] args) {
        launch(args);
    }
}
