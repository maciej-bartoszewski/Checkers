package org.example.checkers.game;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class TurnInfoTwoPlayers {
    private final Game game;
    private final Label turnInfoLabel = new Label("WHITE move");

    public TurnInfoTwoPlayers(Game game) {
        this.game = game;
    }

    public BorderPane getInfo() {
        HBox turnInfoBox = new HBox(turnInfoLabel);
        turnInfoBox.setAlignment(Pos.CENTER);
        turnInfoBox.setFillHeight(true);

        turnInfoLabel.setFont(new Font("Arial", 22));
        turnInfoLabel.setTextFill(Color.WHITE);

        BorderPane bottomPane = new BorderPane();
        bottomPane.setCenter(turnInfoBox);

        return bottomPane;
    }

    public void updateTurnLabel() {
        turnInfoLabel.setText(game.getTurn() + " move");
    }
}
