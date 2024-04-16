package org.example.checkers.game;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class TurnInfoOnline {
    private final Game game;
    private final Label colorInfoLabel = new Label("You are White");
    private final Label turnInfoLabel = new Label("Your move");

    public TurnInfoOnline(Game game) {
        this.game = game;
    }

    public BorderPane getInfo() {
        HBox colorInfoBox = new HBox(colorInfoLabel);
        colorInfoBox.setAlignment(Pos.CENTER);
        colorInfoBox.setFillHeight(true);

        colorInfoLabel.setFont(new Font("Arial", 22));
        colorInfoLabel.setTextFill(Color.WHITE);

        HBox turnInfoBox = new HBox(turnInfoLabel);
        turnInfoBox.setAlignment(Pos.CENTER);
        turnInfoBox.setFillHeight(true);

        turnInfoLabel.setFont(new Font("Arial", 22));
        turnInfoLabel.setTextFill(Color.WHITE);

        VBox infoVBox = new VBox(colorInfoBox, turnInfoBox);

        BorderPane bottomPane = new BorderPane();
        bottomPane.setCenter(infoVBox);

        return bottomPane;
    }

    public void updateColorLabel() {
        colorInfoLabel.setText("You are " + game.getPlayerColor());
    }

    public void updateTurnLabel() {
        if(game.getPlayerColor() == game.getTurn()) turnInfoLabel.setText("Your move");
        else turnInfoLabel.setText("Opponent's move");
    }
}
