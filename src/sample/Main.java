package sample;

import javafx.animation.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.Group;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import pong.Impact;
import pong.PongGame;
import pong.Net;
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
    PongGame game;
    Canvas gameRender;
    GraphicsContext gameGraphics;
    double bX;
    double bY;
    double bG;
    double bYi;
    double ballTravel;
    double spacerFrame = 10;


    @Override
    public void init(){
        screenWidth = 800;
        screenHeight = 600;
        game = new PongGame(screenWidth, screenHeight,true);

        game.pongBall.setBallBounds(screenWidth, screenHeight);

        ballCalculateLinearValues();

        gameRender = new Canvas(
                screenWidth,
                screenHeight
        );
        gameGraphics = gameRender.getGraphicsContext2D();

    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Drawing Lines");
        Group root = new Group();
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        EventHandler<KeyEvent> playerInput = new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent ke) {
                KeyCode key = ke.getCode();
                if (key == KeyCode.LEFT) {
                    game.players[0].paddle.moveUp();
                }

                if (key == KeyCode.RIGHT) {
                    game.players[0].paddle.moveDown();
                }
            }
        };
        scene.addEventFilter(KeyEvent.KEY_PRESSED, playerInput);


        root.getChildren().add(gameRender);



        Timeline gameloop = new Timeline();

        /* loop forever */
        gameloop.setCycleCount(Timeline.INDEFINITE);

        /* prevent reverse animation */
        gameloop.setAutoReverse(false);

        /* implements the game loop */
        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.01), new EventHandler<ActionEvent>(){

            @Override
            public void handle(ActionEvent ae) {
                clearGraphics();
                Impact  aBallImpact = game.isThereABallImpact(bX, bY);
                boolean delay = false;
                if (aBallImpact != null) {

                    if (aBallImpact.toString().equals("net")) {
                        game.registerScore((Net) aBallImpact);
                        game.serveBall();


                    }
                    else {
                        game.pongBall.impact(aBallImpact, (int) bX, (int) bY);
                    }
                    ballCalculateLinearValues();
                }
                if (!(
                        bX > 0 &&
                        bX + (game.pongBall.getDiameter() / 2.0) < screenWidth &&
                        bY > 0 &&
                        bY + (game.pongBall.getDiameter() / 2.0) < screenHeight
                )) {
                    game.pongBall.nextMove();
                    ballCalculateLinearValues();
                }
                if (game.gameEnds) {
                    drawGameOver();
                    gameloop.stop();
                }
                else {

                    ballChangeXYPos();
                    drawPaddles();
                    drawBall();
                }
            }
        });

        gameloop.getKeyFrames().add(keyFrame);
        primaryStage.show();
        gameloop.play();

    }



    private void ballCalculateLinearValues() {

        bX = game.pongBall.getPreviousX();
        bY = game.pongBall.getPreviousY();

        bG = Calculate.gradientOfLine(
                game.pongBall.getPreviousX(),
                game.pongBall.getPreviousY(),
                game.pongBall.getX(),
                game.pongBall.getY()
        );

        bYi = Calculate.yIntercept(
                bG,
                game.pongBall.getX(),
                game.pongBall.getY()
        );

        ballTravel = Calculate.distanceBetweenPoints(
                game.pongBall.getPreviousX(),
                game.pongBall.getPreviousY(),
                game.pongBall.getX(),
                game.pongBall.getY()
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
        double step = screenWidth / (200 - game.pongBall.getSpeed());



        // When the slope ratio is greater than |1| the x coordinate of the line is
        // calculated rather than the y as it provides more accurate result.
        if (Math.abs(bG) < 1) {

            // if the ball is moving towards 0 on the x axis, step is negative.
            if (game.pongBall.getXTravel() == -1) {
                step = -(step);
            }
            bX += step;
            bY = Calculate.yCoordinate(bG, bX, bYi);
        }
        else {
            // if the ball is moving towards 0 on the y axis, step is negative.
            if (game.pongBall.getYTravel() == -1) {
                step = -(step);
            }
            bY += step;
            bX = Calculate.xCoordinate(bG, bY, bYi);
        }
        //System.out.println("x" + bX + " y" +bY);


    }

    private void clearGraphics() {
        gameGraphics.setFill(Color.BLACK);
        gameGraphics.fillRect(0,0, gameRender.getWidth(), gameRender.getHeight());
    }


    private void drawBall() {
        gameGraphics.setFill(Color.WHITE);
        gameGraphics.fillOval(
                bX-(game.pongBall.getDiameter()/2.0),
                bY-(game.pongBall.getDiameter()/2.0),
                game.pongBall.getDiameter(), game.pongBall.getDiameter());
    }
    private void drawPaddles() {
        gameGraphics.setFill(Color.WHITE);
        for (int i = 0; i < game.paddles.length; i++) {
            if (game.paddles[i] != null) {
                gameGraphics.fillRect(
                        game.paddles[i].getPosX(),
                        game.paddles[i].getPosY(),
                        game.paddles[i].getWidth(),
                        game.paddles[i].getHeight()
                );
            }
        }
    }

    private void drawGameOver() {
        gameGraphics.setFill(Color.WHITE);
        gameGraphics.fillText("GAME OVER",100,100);

    }



    public static void main(String[] args) {
        launch(args);
    }
}
