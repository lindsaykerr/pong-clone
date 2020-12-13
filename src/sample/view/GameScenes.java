package sample.view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

/**
 * Holds methods which build Scenes for specific game screen layouts
 */
public class GameScenes {
    static final Font gameFont = Font.font("Consolas");

    /**
     * Builds the start screen for the game
     * @return a Scene
     */
    static public Scene startScene(int sceneWidth, int sceneHeight) {

        Label gameTitle = new Label("Pong Clone");
        gameTitle.setFont(Font.font(gameFont.getFamily(), 90));
        gameTitle.setTextFill(Color.WHITE);
        gameTitle.setStyle("-fx-font-weight:bold;");

        VBox userText = new VBox(10);

        Label play = new Label("<Enter> to play");
        play.setFont(Font.font(gameFont.getFamily(), 24));
        play.setTextFill(Color.WHITE);

        Label quit = new Label("<Esc> to Quit  ");
        quit.setFont(Font.font(gameFont.getFamily(), 24));
        quit.setTextFill(Color.WHITE);

        userText.getChildren().addAll(play, quit);
        userText.setAlignment(Pos.TOP_CENTER);
        userText.setPadding(new Insets(0,0,35,0));

        BorderPane root = new BorderPane();
        root.setCenter(gameTitle);
        root.setBottom(userText);
        root.setStyle("-fx-background-color: black;");

        return new Scene(root, sceneWidth, sceneHeight);
    }

    /**
     * Builds the main game screen for pong
     * @param gameGraphics Canvas Object
     * @return a Scene
     */
    static public Scene gameScene(int sceneWidth, int sceneHeight, Canvas gameGraphics) {
        Label gameScore = new Label("Score");
        gameScore.setFont(Font.font(gameFont.getFamily(), 24));
        gameScore.setStyle("-fx-font-weight:bold;");
        gameScore.setTextFill(Color.WHITE);

        VBox gamePane = new VBox(20);
        gameGraphics.setStyle("-fx-background-color: blue;");
        gamePane.setStyle("-fx-background-color: black;");
        gamePane.setAlignment(Pos.CENTER);
        gamePane.getChildren().addAll(gameGraphics, gameScore);

        return new Scene(gamePane, sceneWidth, sceneHeight);
    }
}
