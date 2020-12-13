package sample.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import pong.Paddle;
import pong.PongGame;

/**
 * Draws game elements onto a JavaFX canvas
 */
public class DrawPong {
    Canvas gameRender;
    GraphicsContext gameGraphics;
    PongGame game;
    Color bgColour;
    Color objColour;

    /**
     * Allows Pong game elements based on the state of the game model to
     * be drawn to a JavaFX Canvas.
     *
     * @param gameRender the JavaFX Canvas that DrawPong will draw to
     * @param gameModel used by DrawingPong instance to provide values for positioning and drawing elements
     * @param bgColour the background colour of the game
     * @param objColour elements colour
     */
    public DrawPong(Canvas gameRender, PongGame gameModel, Color bgColour, Color objColour){

        this.gameRender = gameRender;
        this.gameGraphics = gameRender.getGraphicsContext2D();
        this.game = gameModel;
        this.bgColour = bgColour;
        this.objColour = objColour;

    }

    /**
     * Clears the canvas with a solid colour
     */
    public void clearGraphics() {
        gameGraphics.setFill(bgColour);
        gameGraphics.fillRect(0,0, gameRender.getWidth(), gameRender.getHeight());
    }

    /**
     * Draws the ball at an XY position
     */
    public void drawBall() {
        gameGraphics.setFill(objColour);
        gameGraphics.fillOval(
                game.ball.getXPos() - game.ball.getRadius(),
                game.ball.getYPos() - game.ball.getRadius(),
                game.ball.getDiameter(),
                game.ball.getDiameter());
    }


    /**
     * Draws paddles based on the position provided by the game model
     */
    public void drawPaddles() {
        gameGraphics.setFill(objColour);

        drawPaddle(game.paddles[0]);

        if(!game.isSinglePlayerGame) {
            drawPaddle(game.paddles[1]);
        }

    }

    public void render() {
        clearGraphics();
        drawPaddles();
        drawBall();
    }

    // Draws a game paddle
    private void drawPaddle(Paddle paddle) {
        gameGraphics.fillRect(
                paddle.getPosX(),
                paddle.getPosY(),
                paddle.getWidth(),
                paddle.getHeight()
        );
    }

    /**
     * Shows a simple game over message.
     */
    public void drawGameOver() {
        gameGraphics.setFill(objColour);
        gameGraphics.fillText("GAME OVER",100,100);

    }

    /**
     * Returns the JavaFX canvas used to render elements
     * @return a JavaFX canvas
     */
    public Canvas getCanvas() {
        return gameRender;
    }
}
