package org.example.checkers.game;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class GameOver {
    private final Game game;

    public GameOver(Game game) {
        this.game = game;
    }

    public void displayEndGameMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Game Over");
        alert.setHeaderText(null);

        if (game.getNumberOfBlackPieces() == 0) alert.setContentText("Game Over! White won!");
        if (game.getNumberOfWhitePieces() == 0) alert.setContentText("Game Over! Black won!");

        ButtonType quitButton = new ButtonType("Quit");
        alert.getButtonTypes().setAll(quitButton);

        alert.showAndWait().ifPresent(buttonType -> {
            if (buttonType == quitButton) {
                game.getTimeInfo().stopCounting();
                System.exit(0);
            }
        });
    }
}
